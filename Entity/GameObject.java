package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class GameObject {
    public Rectangle Body;
    public Color BackgroundColor;

    public Point GetSize() {
        return new Point(Body.width, Body.height);
    }

    public Point GetPosition() {
        return new Point(Body.x, Body.y);
    }

    public abstract void Update(float delta);
    public abstract void paintComponent(Graphics g);
}
