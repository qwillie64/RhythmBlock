package UI;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public abstract class UiComponent {
    public Point Position;
    public Point Size;
    public Color BackGroundColor;
    public BufferedImage Image;
    public Font Font;
    
    public UiComponent() {
        this.Position = new Point(0, 0);
        this.Size = new Point(0, 0);
        this.BackGroundColor = Color.BLACK;
        this.Image = null;
        this.Font = null;
    };

    public Rectangle getBody() {
        return new Rectangle(Position.x, Position.y, Size.x, Size.y);
    }

    public void setCenterPosition(Point position) {
        this.Position.x = position.x - Size.x / 2;
        this.Position.y = position.y - Size.y / 2;
    }

    public void setCenterPosition(int x, int y) {
        this.Position.x = x - Size.x / 2;
        this.Position.y = y - Size.y / 2;
    }
    
    public abstract void update(float delta);
    public abstract void draw(Graphics g);
    public abstract void show();
    public abstract void close();
}
