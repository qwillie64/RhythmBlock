package Tool;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class MusicPlayer {

    public static String FilePath;
    public static long Start;
    public static Clip Audio;

    public static void LoadFile(String path) {
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

    public static void SetStartPosition(long ms) {
        Start = ms * 1000;
        Audio.setMicrosecondPosition(Start);
    }

    public long GetCurrentTime() {
        return Audio.getMicrosecondPosition();
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
