
import java.awt.Graphics;
import java.awt.Rectangle;

public class ClickBlock extends Block{
    
    public ClickBlock(Rectangle body, float speed, int hitKey) {
        super(body, speed, hitKey);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillRect(Body.x, Body.y, Body.width, Body.height);
    }

    @Override
    public void Update(float delta) {
        
        if (InputListener.IsKeyClick(HitKey) && Tool.IsCollision(Body, GameObjectManager.DetectLine.Body)) {
            State = BlockState.FINISH;
        }
        
        Body.y = (int) (Body.y + Speed * delta);
    }
}
