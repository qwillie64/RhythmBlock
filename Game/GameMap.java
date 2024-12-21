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
    private Point size;

    public final int CHANNEL_WIDTH = 100;

    public static List<Block> ActiveBlockCollection;
    public static List<Block> StayBlockCollection;
    public static DetectLine DetectArea;

    
    public GameMap() {
        size = new Point(0, 0);
        ActiveBlockCollection = new LinkedList<>();
        StayBlockCollection = new LinkedList<>();
        DetectArea = DetectLine.Empty();
    }
    
    public void read(String path, Point screenSize) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("Assests/Daydreams.json")));
            JSONObject jsonObject = new JSONObject(content);

            // read data
            bpm = jsonObject.getFloat("bpm");
            start = jsonObject.getInt("start");
            name = jsonObject.getString("name");
            JSONArray blocks = jsonObject.getJSONArray("blocks");


            int block_h = 20;
            int block_y = -block_h;
            
            // detect
            DetectArea = new DetectLine(new Rectangle(0, 2 * screenSize.y / 3, 4 * CHANNEL_WIDTH, block_h));

            time_d = (int) ((DetectArea.Body.y - block_y) * 1000000f / Setting.getSpeed());

            //blocks
            for (int i = 0; i < blocks.length(); i++) {
                JSONObject block = blocks.getJSONObject(i);
                int channel = block.getInt("channel");
                int b_start = block.getInt("start");
                int type = block.getInt("type");
                int period = block.getInt("period");

                Rectangle body = new Rectangle(channel * CHANNEL_WIDTH + 2, block_y, CHANNEL_WIDTH - 4, block_h);
                switch (type) {
                    case 0:
                        ClickBlock cb = new ClickBlock(body, Setting.KeySet.get(channel), b_start);
                        cb.setMovement(body.getLocation(), DetectArea.Body.getLocation(), time_d / 1000000f);
                        StayBlockCollection.add(cb);
                        break;
                        
                    case 1:
                        PressBlock pb = new PressBlock(body, Setting.KeySet.get(channel), b_start, (int) (Setting.getSpeed() * period / 1000000f));
                        pb.setMovement(body.getLocation(), DetectArea.Body.getLocation(), time_d / 1000000f);
                        StayBlockCollection.add(pb);
                        break;

                    default:
                        throw new AssertionError();
                }
            }
            
            this.size = new Point(4 * CHANNEL_WIDTH, screenSize.y);
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
            if (off >= 0) {
                block.current += off / 1000000f;
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

    public Point getSize() {
        return size;
    }
}