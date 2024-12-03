import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PressBlock extends Block {
    public float HitRate;
    public Rectangle HitBody;

    public PressBlock(Rectangle body, float speed, int hitKey, float hitRate) {
        super(body, speed, hitKey);
        this.HitRate = hitRate;
        this.HitBody = Tool.GetPart(body, hitRate);
        this.color = Color.GRAY;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillRect(Body.x, Body.y, Body.width, Body.height);

        g.setColor(color.brighter());
        g.fillRect(HitBody.x, HitBody.y, HitBody.width, HitBody.height);
    }

    @Override
    public void Update(float delta) {
        if (State == BlockState.ACTIVE) {
            // 超線未點 -> Miss, State.DEAD
            if (Tool.IsOver(HitBody, GameObjectManager.DetectLine.Body)) {
                State = BlockState.DEAD;
                return;
            }

            // 點擊成功 -> State.KEEP
            if (InputListener.IsKeyPress(HitKey) && Tool.IsCollision(HitBody, GameObjectManager.DetectLine.Body)) {
                State = BlockState.KEEP;
            }
        }
        else if (State == BlockState.KEEP) {
            // 過線 -> State.FINISH
            if (Tool.IsOver(Body, GameObjectManager.DetectLine.Body)) {
                State = BlockState.FINISH;
            }
            
            // 長按失敗 -> State.DEAD
            if (!InputListener.IsKeyPress(HitKey) && Tool.IsCollision(Body, GameObjectManager.DetectLine.Body)) {
                State = BlockState.DEAD;
                return;
            }
        }

        Body.y = (int) (Body.y + Speed * delta);
        HitBody = Tool.GetPart(Body, HitRate);
    }
}
