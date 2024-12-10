package Tool;


import java.awt.Color;
import java.awt.Rectangle;

public class Tool {
    public static Color Combine(Color c, int alpha) {
        if (alpha < 0) {
            alpha = 0;
        }
        
        Color tc = new Color(
            c.getRed(),
            c.getGreen(),
            c.getBlue(),
            alpha
        );

        return tc;
    }
    
    public static boolean IsCollision(Rectangle r1, Rectangle r2) {
        return r1.y + r1.height >= r2.y && r1.y < r2.y;
    }

    public static float GetCollisionPart(Rectangle r1, Rectangle r2) {
        return (r1.y - r2.y) / (float) r1.height;
    }

    public static boolean IsOver(Rectangle r1, Rectangle r2) {
        return r1.y > r2.y + r2.height;
    }

    public static Rectangle GetPart(Rectangle r, float rate) {
        int ny = (int) (rate * r.height);
        return new Rectangle(r.x, r.y + r.height - ny, r.width, ny);
    }
}
