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
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;


public class GameMap {
    private float bpm;
    private int start;
    private String name;
    private int time_d;
    private Point screenSize;

    public final int CHANNEL_WIDTH = 100;

    public static List<Block> ActiveBlockCollection;
    public static List<Block> StayBlockCollection;
    public static DetectLine DetectArea;

    
    public GameMap() {
        screenSize = new Point(0, 0);
        ActiveBlockCollection = new LinkedList<>();
        StayBlockCollection = new LinkedList<>();
        DetectArea = DetectLine.Empty();
    }
    
    public void read(String path, Point size) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("Assests/Daydreams.json")));
            JSONObject jsonObject = new JSONObject(content);

            // read data
            bpm = jsonObject.getFloat("bpm");
            start = jsonObject.getInt("start");
            name = jsonObject.getString("name");
            JSONArray blocks = jsonObject.getJSONArray("blocks");

            // process block
            float b_height = (Setting.Speed * 60f / bpm) / 4f;
            DetectArea = new DetectLine(new Rectangle(0, 2 * size.y / 3, 4 * CHANNEL_WIDTH, (int) b_height));
            time_d = (int) (DetectArea.Body.getCenterY() * 1000000f / Setting.Speed);
            for (int i = 0; i < blocks.length(); i++) {
                JSONObject block = blocks.getJSONObject(i);
                int channel = block.getInt("channel");
                int b_start = block.getInt("start");
                int type = block.getInt("type");
                int period = block.getInt("period");

                switch (type) {
                    case 0:
                        StayBlockCollection.add(new ClickBlock(
                                new Rectangle(channel * CHANNEL_WIDTH + 2, -(int) b_height, CHANNEL_WIDTH - 4,
                                        (int) b_height),
                                Setting.Speed, Setting.KeySet.get(channel), b_start));
                        break;
                    case 1:
                        StayBlockCollection.add(new PressBlock(
                                new Rectangle(channel * CHANNEL_WIDTH + 2, -(int) b_height, CHANNEL_WIDTH - 4,
                                        (int) b_height),
                                Setting.Speed, Setting.KeySet.get(channel), b_start,
                                (int) (Setting.Speed * period / 1000000f)));
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            
            screenSize = new Point(4 * CHANNEL_WIDTH, size.y);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void update(float delta) {
        for (Block block : ActiveBlockCollection) {
            block.Update(delta);
        }

        
        for (Block block : StayBlockCollection) {
            int off = MusicPlayer.GetCurrentTime() - block.TimeMark + time_d;
            if (off >= -55000) {
                block.Body.y -= (int) ((off / 1000000f) * Setting.Speed);
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

        DetectArea.paintComponent(g);
    }

    public Point getScreenSize() {
        return screenSize;
    }
}