package Main;

import Entity.ClickBlock;
import Entity.PressBlock;
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
            int bdp = (int) (6 * (float) 60 / BPM);
            int y = ObjectManager.DetectLine.Body.y - bdp * 250 - note.duration * 20;

            switch (note.type) {
                case 0:
                    ObjectManager.AddBlock_Stay(new ClickBlock(new Rectangle(note.channel * 60, y, 50, note.duration * 30),
                            250f, Setting.KeySet.get(note.channel), note.time - bdp * 1000000 - 4000, note.duration));
                    break;
                case 1:
                    ObjectManager.AddBlock_Stay(new PressBlock(new Rectangle(note.channel * 60, y, 50, note.duration * 30),
                            250f, Setting.KeySet.get(note.channel), 0.3f, note.time - bdp * 1000000 - 4000, note.duration));
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }
}




