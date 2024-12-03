import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class GameObjectManager {
    public static List<Block> BlockCollection = new LinkedList<>();
    public static DetectLine DetectLine;

    public static void Add(GameObject gameObject) {
        if (gameObject.getClass() == Block.class) {
            BlockCollection.add((Block) gameObject);
        }
        else if (gameObject.getClass() == DetectLine.class) {
            DetectLine = (DetectLine) gameObject;
        }
        
    }
    
    public static void Update(float delta) {
        BlockCollection.removeIf(obj -> obj.IsHit);

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
