import java.awt.Graphics;
import java.awt.Point;

public abstract class GameObject {
    public Point Position;
    public Point Size;

    public abstract void Update(float delta);
    public abstract void paintComponent(Graphics g);
}
