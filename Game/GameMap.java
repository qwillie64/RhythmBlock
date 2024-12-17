package Game;

import org.json.*;

import Entity.Block;
import Entity.ClickBlock;
import Entity.DetectLine;
import Entity.PressBlock;
import State.BlockState;
import Tool.MusicPlayer;
import Tool.Setting;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;


public class GameMap {
    private float bpm;
    private int start;
    private String name;

    public static List<Block> ActiveBlockCollection;
    public static List<Block> StayBlockCollection;
    public static DetectLine DetectLine;

    
    public GameMap() {
        ActiveBlockCollection = new LinkedList<>();
        StayBlockCollection = new LinkedList<>();
        DetectLine = null;
    }

    public void SetDetectLine(DetectLine detectLine) {
        DetectLine = detectLine;
    }
    
    public void read(String path) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("Assests/Daydreams.json")));
            JSONObject jsonObject = new JSONObject(content);

            // read data
            bpm = jsonObject.getFloat("bpm");
            start = jsonObject.getInt("start");
            name = jsonObject.getString("name");
            JSONArray blocks = jsonObject.getJSONArray("blocks");

            // process block
            int bdp = (int) (6 * 60f / bpm);
            for (int i = 0; i < blocks.length(); i++) {
                JSONObject block = blocks.getJSONObject(i);
                int channel = block.getInt("channel");
                int b_start = block.getInt("start");
                int type = block.getInt("type");
                int period = block.getInt("period");
                int y = DetectLine.Body.y - bdp * (int)Setting.Speed - (period/100) * 20;

                switch (type) {
                    case 0:
                        StayBlockCollection.add(new ClickBlock(
                                new Rectangle(channel * 60, y, 50, 30),
                                Setting.Speed, Setting.KeySet.get(channel), b_start - bdp * 1000000 - 4000,
                                period));
                        break;
                    case 1:
                        StayBlockCollection.add(new PressBlock(
                                new Rectangle(channel * 60, -100, 50, 90),
                                Setting.Speed, Setting.KeySet.get(channel), 0.3f, b_start - bdp * 1000000 - 4000,
                                period));
                        break;
                    default:
                        throw new AssertionError();
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void update(float delta) {
        for (Block block : ActiveBlockCollection) {
            block.Update(delta);
        }

        // 微秒誤差 : 方塊早多久出現
        int offset = 150;
        System.out.println(MusicPlayer.GetCurrentTime());
        for (Block block : StayBlockCollection) {
            int t_offset = MusicPlayer.GetCurrentTime() + offset - block.TimeMark;
            if (t_offset >= 0) {
                block.Body.y += (int) (block.Speed * t_offset / 1000000);
                block.State = BlockState.ACTIVE;
                ActiveBlockCollection.add(block);
            }
        }

        ActiveBlockCollection.removeIf(obj -> obj.State == BlockState.FINISH);
        StayBlockCollection.removeIf(obj -> obj.State == BlockState.ACTIVE);
    }
    
    public void paintComponent(Graphics g) {
        for (Block block : ActiveBlockCollection) {
            block.paintComponent(g);
        }

        DetectLine.paintComponent(g);
    }
}