package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapDetail {
    public class Note {
        public int channel;
        public int type;
        public int time;
        public int duration;
    }
    
    public int BPM;
    public int Offset;
    public int Start;
    public ArrayList<Note> Notes;

    public void Read(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println(data[0] + data[1] + data[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




