package UI;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public abstract class UiComponent {
    public Point Position;
    public Point Size;
    public Color BackGroundColor;
    public BufferedImage Image;
    
    public UiComponent() {};

    public Rectangle getBody() {
        return new Rectangle(Position.x, Position.y, Size.x, Size.y);
    }

    public abstract void update(float delta);
    public abstract void draw(Graphics g);
    public abstract void show();
    public abstract void close();
}
