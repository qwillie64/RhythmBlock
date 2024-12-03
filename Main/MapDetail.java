package Main;

import Entity.ClickBlock;
import Tool.Setting;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class MapDetail {
    public class Note {
        public int channel;
        public int type;
        public int time;
        public int duration;

        public Note(int channel, int type, int time, int duration) {
            this.channel = channel;
            this.type = type;
            this.time = time;
            this.duration = duration;
        }
    }
    
    public int BPM = 165;
    public int Offset = 0;
    public int Start;
    public ArrayList<Note> Notes = new ArrayList<>();

    public void Read(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Notes.add(new Note(
                        Integer.parseInt(data[0]),
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3])));

                // System.out.println(data[2]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (Note note : Notes) {
            switch (note.type) {
                case 0:
                    int y = ObjectManager.DetectLine.Body.y - (int)(6 * (float)60 / BPM) * 250 - note.duration * 20;
                    ObjectManager.AddBlock_Stay(new ClickBlock(new Rectangle(note.channel * 60, y, 50, note.duration * 20),
                            250, Setting.KeySet.get(note.channel), note.time - (int)(6 * (float)60 / BPM) * 1000000, note.duration));
                    break;
                case 1:
                    
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }
}




