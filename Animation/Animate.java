package Animation;

import java.awt.Point;

public class Animate {
    public static Point Linear(Point start, Point end, float t) {
        Point move = new Point(end.x - start.x, end.y - start.y);
        int x = start.x + (int)(move.x * t);
        int y = start.y + (int)(move.y * t);
        return new Point(x, y);
    }

    public static int Linear(int start, int end, float t) {
        return start + (int) ((end - start) * t);
    }

    public static int EaseIn(int start, int end, float t, float alpha) {
        return start + (int) ((end - start) * Math.pow(t, alpha));
    }
}
