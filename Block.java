import java.awt.Graphics;
import java.awt.Point;

public class Block extends GameObject {
    public float speed;

    public Block(Point position) {
        this.Position = position;
        this.Size = new Point(50, 50);
    }

    public void Start() {
        
    }

    @Override
    public void paintComponent(Graphics g) {
        g.fillRect(Position.x, Position.y, Size.x, Size.y);
    }


    @Override
    public void Update(float delta) {
        Position.setLocation(Position.x, Position.x + speed * delta);
    }
}
