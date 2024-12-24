package Tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

public class SoundManager {
    public enum SoundType {
        SOUND, MUSIC
    }
    public enum PlayType {
        NEW, CURRENT, WAIT
    }
    private final static Map<String ,AudioInputStream> music = new HashMap<>();
    private final static Map<String ,AudioInputStream> sounds = new HashMap<>();
    private final static Map<Integer ,Clip> playing = new HashMap<>();
    private static int idCounter = 0;

    public static void load(String path, String name, SoundType type) {
        try {
            File audioFile = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            switch (type) {
                case SOUND:
                    sounds.put(name, audioStream);
                    break;
                case MUSIC:
                    music.put(name, audioStream);
                    break;
                default:
                    throw new AssertionError();
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static int getCurrentTime(int id) {
        try {
            return (int) playing.get(id).getMicrosecondPosition();
        } catch (Exception e) {
            return -1;
        }
    }
    
    public static int prepare(String name, int start, SoundType type) {
        Clip clip;
        switch (type) {
            case SOUND:
                clip = copy(sounds.get(name));
                break;
            case MUSIC:
                clip = copy(music.get(name));
                break;
            default:
                return -1;
        }

        clip.setMicrosecondPosition(start);
        playing.put(idCounter, clip);
        int id = idCounter;
        idCounter += 1;
        return id;
    }

    public static void play(int id) {
        playing.get(id).start();
    }
    
    public static void pause(int id) {
        playing.get(id).stop();
    }

    public static void remove(int id) {
        playing.remove(id);
    }

    public static void setVolume(int id, float volume) {
        Clip clip = playing.get(id);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    public static float getVolume(int id) {
        Clip clip = playing.get(id);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10, gainControl.getValue() / 20);
    }
    
    public static short[] getSample(String name, SoundType type) {
        AudioInputStream audioStream = null;
        switch (type) {
            case SOUND:
                audioStream = sounds.get(name);
                break;
            case MUSIC:
                audioStream = music.get(name);
                break;
            default:
                //..
        }

        try {
            AudioFormat format = audioStream.getFormat();
            System.out.println("Audio format: " + format);
            System.out.println("Audio stream available: " + (audioStream != null));
            System.out.println("Encoding: " + format.getEncoding());
            System.out.println("Frame Length: " + audioStream.getFrameLength());
            System.out.println("Frame Size: " + format.getFrameSize());
            System.out.println("Sample Size: " + format.getSampleSizeInBits());

            int sampleSizeInBits = format.getSampleSizeInBits();
            int bytesPerSample = sampleSizeInBits / 8; // 每个样本占用字节数
            boolean isBigEndian = format.isBigEndian();
            int channels = format.getChannels(); // 声道数
            int bytesPerFrame = format.getFrameSize();

            if (sampleSizeInBits != 16 || channels != 2 || bytesPerFrame != 4) {
                System.err.println("Unsupported format: only 16-bit stereo PCM is supported.");
                return null;
            }

            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = audioStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, bytesRead);
            }
            byte[] audioBytes = byteBuffer.toByteArray();
            System.out.println("Total bytes read: " + audioBytes.length);

            // 验证读取字节数是否与预期一致
            int expectedBytes = (int) audioStream.getFrameLength() * bytesPerFrame;
            if (audioBytes.length != expectedBytes) {
                System.err.println("Warning: Read bytes (" + audioBytes.length + ") do not match expected bytes (" + expectedBytes + ").");
            }

            // 转换为 short[] 数据
            int numSamples = audioBytes.length / bytesPerSample;
            short[] leftChannel = new short[numSamples / channels];
            short[] rightChannel = new short[numSamples / channels];

            for (int i = 0; i < numSamples; i += channels) {
                int byteIndex = i * bytesPerSample;

                // 提取左声道数据
                if (isBigEndian) {
                    leftChannel[i
                            / channels] = (short) ((audioBytes[byteIndex] << 8) | (audioBytes[byteIndex + 1] & 0xFF));
                    rightChannel[i / channels] = (short) ((audioBytes[byteIndex + 2] << 8)
                            | (audioBytes[byteIndex + 3] & 0xFF));
                } else {
                    leftChannel[i
                            / channels] = (short) ((audioBytes[byteIndex + 1] << 8) | (audioBytes[byteIndex] & 0xFF));
                    rightChannel[i / channels] = (short) ((audioBytes[byteIndex + 3] << 8)
                            | (audioBytes[byteIndex + 2] & 0xFF));
                }
            }

            // for (int i = 0; i < leftChannel.length; i++) {
            //     leftChannel[i] = (short) (20f * (float) Math.log10(leftChannel[i]) * 100f);
            // }

            return leftChannel; // 返回左声道数据作为示例
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static float calculateVolume(short[] samples) {
        long sum = 0;
        for (short sample : samples) {
            sum += Math.abs(sample);
        }
        return sum / (float) samples.length;
    }
    
    public static short[] _LowPassFilter(short[] sample, int filterSize) {
        short[] filteredSamples = new short[sample.length];

        for (int i = 0; i < sample.length; i++) {
            int sum = 0;
            int count = 0;

            for (int j = i - filterSize; j <= i + filterSize; j++) {
                if (j >= 0 && j < sample.length) {
                    sum += sample[j];
                    count++;
                }
            }

            filteredSamples[i] = (short) (sum / count);
        }

        return filteredSamples;
    }

    public static short[] _highPassFilter(short[] sample, int filterSize) {
        short[] filteredSamples = new short[sample.length];

        for (int i = sample.length - 1; i >= 0; i--) {
            int sum = 0;
            int count = 0;

            for (int j = i - filterSize; j <= i + filterSize; j++) {
                if (j >= 0 && j < sample.length) {
                    sum += sample[j];
                    count++;
                }
            }

            filteredSamples[i] = (short) (sum / count);
        }

        return filteredSamples;
    }

    private static Clip copy(AudioInputStream inputStream) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            return clip;
        } catch (LineUnavailableException | IOException e) {
            return null;
        }
    }
}
