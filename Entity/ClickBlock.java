package Entity;

import Animation.Animate;
import Game.GameMap;
import State.BlockState;
import State.Judgment;
import Tool.*;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ClickBlock extends Block {
    
    public int y = 0;
    public float period = 0;
    public float current = 0;
    public int lastupdate = 0;
    public ClickBlock(Rectangle body, float speed, int hitKey, int timeMark) {
        super(body, speed, hitKey, timeMark);
        y = body.y;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(BackgroundColor);
        g.fillRect(Body.x, Body.y, Body.width, Body.height);
    }

    @Override
    public void Update(float delta) {
        current += delta;

        if (State == BlockState.ACTIVE) {
            // 點擊成功 -> State.FINISH
            if (InputListener.IsKeyClick(HitKey) && GameMap.DetectArea.IsCollision(Body)) {
                // System.out.println(InputListener.ToString() + " -> Hit");
                Finish(Judgment.PERFECT);
            }

            // 超線未點 -> Miss, State.DEAD
            if (GameMap.DetectArea.IsOver(Body)) {
                // System.out.println(InputListener.ToString() + " -> Miss");
                Kill();
                current = 0;
                return;
            }

            Body.y = Animate.EaseIn(y, GameMap.DetectArea.Body.y, current / period, 3f);
        }
        else if (State == BlockState.DEAD) {
            Body.y = Animate.Linear(Body.y, Body.y + 100, current / 10);

            if (current / period > 1) {
                State = BlockState.FINISH;
            }
        }
    }
}
