import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class GameObjectManager {
    public static List<GameObject> GameObjectCollect = new LinkedList<>();

    public static void Add(GameObject gameObject) {
        GameObjectCollect.add(gameObject);
    }
    
    public static void Update(float delta) {
        for (GameObject gObject : GameObjectCollect) {
            gObject.Update(delta);
        }
    }

    public static void paintComponent(Graphics g) {
        for (GameObject gObject : GameObjectCollect) {
            gObject.paintComponent(g);
        }
    }
}
