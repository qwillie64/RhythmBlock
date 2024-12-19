package Entity;

import Game.GameMap;
import State.*;
import Tool.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PressBlock extends Block {
    public Rectangle TailBody;

    public PressBlock(Rectangle body, float speed, int hitKey, int timeMark, int tailHeight) {
        super(body, speed, hitKey, timeMark);
        this.TailBody = new Rectangle(body.x + body.width / 5, body.y - tailHeight, body.width - 2 * body.width / 5, tailHeight);
        this.BackgroundColor = Color.GRAY;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(BackgroundColor);
        g.fillRect(Body.x, Body.y, Body.width, Body.height);

        g.setColor(BackgroundColor.brighter());
        g.fillRect(TailBody.x, TailBody.y, TailBody.width, TailBody.height);
    }

    @Override
    public void Update(float delta) {
        if (State == BlockState.ACTIVE) {
            // 超線未點 -> Miss, State.DEAD
            if (GameMap.DetectArea.IsOver(Body)) {
                Kill();
                return;
            }

            // 點擊成功 -> State.KEEP
            if (InputListener.IsKeyClick(HitKey) && GameMap.DetectArea.IsCollision(Body)) {
                State = BlockState.KEEP;
                BackgroundColor = Color.YELLOW;
            }
        }
        else if (State == BlockState.KEEP) {
            // 過線 -> State.FINISH
            if (GameMap.DetectArea.IsOver(TailBody)) {
                Finish(Judgment.PERFECT);
            }

            // 長按失敗 -> State.DEAD
            if (!InputListener.IsKeyPress(HitKey) && GameMap.DetectArea.IsCollision(TailBody)) {
                Finish(Judgment.GOOD);
                return;
            }
        }
        else if (State == BlockState.DEAD) {
            Speed -= 10;
            Alpha -= 20;
            BackgroundColor = Tool.Combine(BackgroundColor, Alpha);

            if (Speed < 100) {
                State = BlockState.FINISH;
            }
        }

        Body.y = (int) (Body.y + Speed * delta);
        TailBody.y = Body.y - TailBody.height;
    }
}
