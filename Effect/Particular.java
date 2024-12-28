package Effect;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

import Tool.Tool;

public class Particular {
    public float TimeLimit;
    public boolean IsEnd = false;
    private Point size = new Point(2, 2);
    private Point position;
    private Point2D.Float speed;
    private float time;
    public Color color;
    private Color[] colorSet;
    private static float[] dist = { 0.2f, 1f };

    public Particular(Point position, Point2D.Float speed, Color color) {
        this.position = position;
        this.speed = speed;
        this.color = color;
        colorSet = getColorSet();
    }

    public void update(float delta) {
        position.x += (int) (speed.x * delta);
        position.y += (int) (speed.y * delta);

        time += delta;
        if (time >= TimeLimit) {
            IsEnd = true;
        }
    }
    
    public void draw(Graphics2D g) {
        // RadialGradientPaint p = new RadialGradientPaint(position, 5, dist, colorSet);
        // g.setPaint(p);
        // g.fillRect(position.x - 20, position.y - 20, 40, 40);
        g.setColor(color);
        g.fillRect(position.x, position.y, size.x, size.y);
    }
    
    private Color[] getColorSet() {
        return new Color[]{color, Tool.Combine(color, 255)};
    }
}
