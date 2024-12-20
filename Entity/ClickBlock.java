package Entity;

import Animation.Animate;
import Game.GameMap;
import State.BlockState;
import State.Judgment;
import Tool.*;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class ClickBlock extends Block {

    public ClickBlock(Rectangle body , int hitKey, int timeMark) {
        super(body, hitKey, timeMark);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Tool.Combine(BackgroundColor, Alpha));
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
                System.out.println(current);
                setMovement(Body.getLocation(), new Point(Body.x, Body.y + 100), period);
                current = 0;
                return;
            }

            // 更新位置
            // Body.y = Animate.EaseIn(start.y, end.y, current / period, 3f);
            Body.y = Animate.Linear(start.y, end.y, current / period);
        }
        else if (State == BlockState.DEAD) {
            Alpha -= 20;
            Body.y = Animate.Linear(start.y, end.y, current / period);

            if (current / period > 1) {
                State = BlockState.FINISH;
            }
        }
    }
}
