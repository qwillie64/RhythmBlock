package Entity;

import Game.GameMap;
import State.BlockState;
import State.Judgment;
import Tool.*;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ClickBlock extends Block{
    
    public ClickBlock(Rectangle body, float speed, int hitKey, int timeMark, int duration) {
        super(body, speed, hitKey, timeMark, duration);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(BackgroundColor);
        g.fillRect(Body.x, Body.y, Body.width, Body.height);
    }

    @Override
    public void Update(float delta) {
        if (State == BlockState.ACTIVE) {
            // 點擊成功 -> State.FINISH
            if (InputListener.IsKeyClick(HitKey) && Tool.IsCollision(Body, GameMap.DetectLine.Body)) {
                System.out.println(InputListener.ToString() + " -> Hit");
                Finish(Judgment.PERFECT);
            }

            // 超線未點 -> Miss, State.DEAD
            if (Tool.IsOver(Body, GameMap.DetectLine.Body)) {
                System.out.println(InputListener.ToString() + " -> Miss");
                Kill();
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
    }
}
