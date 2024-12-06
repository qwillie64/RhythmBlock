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

        for (Block block : StayBlockCollection) {
            if (MusicPlayer.GetCurrentTime() >= block.TimeMark) {
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
