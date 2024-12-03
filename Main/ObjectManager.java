package Main;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import Entity.*;

public class ObjectManager {
    public static List<Block> BlockCollection = new LinkedList<>();
    public static List<Block> ProcessingBlockCollection = new LinkedList<>();
    public static DetectLine DetectLine;

    public static void AddBlock(Block block) {
        BlockCollection.add(block);
    }
    
    public static void SetDetectLine(DetectLine detectLine) {
        DetectLine = detectLine;
    }
    
    public static void Update(float delta) {
        BlockCollection.removeIf(obj -> obj.State == BlockState.FINISH);
        BlockCollection.removeIf(obj -> obj.State == BlockState.DEAD);

        for (GameObject gObject : BlockCollection) {
            gObject.Update(delta);
        }
    }

    public static void paintComponent(Graphics g) {
        for (GameObject gObject : BlockCollection) {
            gObject.paintComponent(g);
        }

        DetectLine.paintComponent(g);
    }
}