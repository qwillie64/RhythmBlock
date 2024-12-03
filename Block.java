import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Block extends GameObject {
    public float Speed;
    public int HitKey;
    public int TargetLine;
    public boolean IsHit = false;
    public boolean IsMiss = false;

    public Block(Point position, float speed, int hitKey, int targetLine) {
        this.Position = position;
        this.Size = new Point(50, 20);
        this.Speed = speed;
        this.HitKey = hitKey;
        this.TargetLine = targetLine;
        this.color = Color.BLACK;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillRect(Position.x, Position.y, Size.x, Size.y);
    }

    @Override
    public void Update(float delta) {
        if (Position.y > TargetLine && Position.y - Size.y < TargetLine) {
            
        }
        Position.setLocation(Position.x, Position.y + Speed * delta);
    }
}
