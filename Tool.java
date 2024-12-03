
import java.awt.Rectangle;

public class Tool {
    public static boolean IsCollision(Rectangle r1, Rectangle r2) {
        return r1.intersects(r2);
    }
}
