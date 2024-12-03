import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends GameObject {
    public float Speed;
    public int HitKey;
    public boolean IsHit = false;
    public boolean IsMiss = false;

    public Block(Rectangle body, float speed, int hitKey) {
        this.Body = body;
        this.Speed = speed;
        this.HitKey = hitKey;
        this.color = Color.BLACK;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillRect(Body.x, Body.y, Body.width, Body.height);
    }

    @Override
    public void Update(float delta) {
        if (InputListener.IsKeyClick(HitKey) && Tool.IsCollision(Body, GameObjectManager.DetectLine.Body)) {
            IsHit = true;
        }
        
        Body.y = (int) (Body.y + Speed * delta);
    }
}
