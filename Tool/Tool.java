package Tool;


import java.awt.Rectangle;

public class Tool {
    public static boolean IsCollision(Rectangle r1, Rectangle r2) {
        return r1.intersects(r2);
    }

    public static boolean IsOver(Rectangle r1, Rectangle r2) {
        return r1.y - r1.width > r2.y + r2.height;
    }

    public static Rectangle GetPart(Rectangle r, float rate) {
        int ny = (int) (rate * r.height);
        return new Rectangle(r.x, r.y + r.height - ny, r.width, ny);
    }
}
