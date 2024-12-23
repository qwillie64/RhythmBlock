package Tool;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
        return (int) playing.get(id).getMicrosecondPosition();
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
