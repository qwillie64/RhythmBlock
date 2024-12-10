package Main;

import Entity.*;
import Tool.MusicPlayer;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class ObjectManager {
    public static List<Block> ActiveBlockCollection = new LinkedList<>();
    public static List<Block> StayBlockCollection = new LinkedList<>();
    public static DetectLine DetectLine;

    public static void AddBlock_Stay(Block block) {
        StayBlockCollection.add(block);
    }

    public static void AddBlock_Direct(Block block) {
        // ActiveBlockCollection.add(block);
    }
    
    public static void SetDetectLine(DetectLine detectLine) {
        DetectLine = detectLine;
    }
    
    public static void Update(float delta) {
        ActiveBlockCollection.removeIf(obj -> obj.State == BlockState.FINISH);
        // ActiveBlockCollection.removeIf(obj -> obj.State == BlockState.DEAD);
        StayBlockCollection.removeIf(obj -> obj.State == BlockState.ACTIVE);

        for (Block block : ActiveBlockCollection) {
            block.Update(delta);
        }

        // 微秒誤差 : 方塊早多久出現
        int offset = 150000;
        for (Block block : StayBlockCollection) {
            int t_offset = MusicPlayer.GetCurrentTime() + offset - block.TimeMark;
            if (t_offset >= 0) {
                block.Body.y += (int)(block.Speed * t_offset / 1000000);
                block.State = BlockState.ACTIVE;
                ActiveBlockCollection.add(block);
            }
        }
    }

    public static void paintComponent(Graphics g) {
        for (Block block : ActiveBlockCollection) {
            block.paintComponent(g);
        }

        DetectLine.paintComponent(g);
    }
}
