package Tool;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class MusicPlayer {

    public static String FilePath;
    public static long Start;
    public static Clip Audio;
    public static Map<String ,Clip> Sounds = new HashMap<String ,Clip>();

    public static void LoadMusic(String path) {
        FilePath = path;
        try {
            File audioFile = new File(path);

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Audio = (Clip) AudioSystem.getLine(info);
            Audio.open(audioStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void LoadSound(String path, String name) {
        try {
            File audioFile = new File(path);

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Audio = (Clip) AudioSystem.getLine(info);
            Audio.open(audioStream);
            Sounds.put(name, Audio);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void SetStartPosition(long ms) {
        Start = ms * 1000;
        Audio.setMicrosecondPosition(Start);
    }

    public static int GetCurrentTime() {
        return (int)Audio.getMicrosecondPosition();
    }
    
    public static void Play() {
        if (Audio == null) {
            return;
        }

        Audio.start();
    }
    
    public static void Close() {
        FilePath = "";
        Audio.close();
        Audio = null;
    }
}
