package Entity;

import State.*;
import Tool.*;
import Game.GameMap;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PressBlock extends Block {
    public float HitRate;
    public Rectangle HitBody;

    public PressBlock(Rectangle body, float speed, int hitKey, float hitRate, int timeMark, int duration) {
        super(body, speed, hitKey, timeMark, duration);
        this.HitRate = hitRate;
        this.HitBody = Tool.GetPart(body, hitRate);
        this.BackgroundColor = Color.GRAY;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(BackgroundColor);
        g.fillRect(Body.x, Body.y, Body.width, Body.height);

        g.setColor(BackgroundColor.brighter());
        g.fillRect(HitBody.x, HitBody.y, HitBody.width, HitBody.height);
    }

    @Override
    public void Update(float delta) {
        if (State == BlockState.ACTIVE) {
            // 超線未點 -> Miss, State.DEAD
            if (Tool.IsOver(HitBody, GameMap.DetectLine.Body)) {
                Kill();
                return;
            }

            // 點擊成功 -> State.KEEP
            if (InputListener.IsKeyClick(HitKey) && Tool.IsCollision(HitBody, GameMap.DetectLine.Body)) {
                State = BlockState.KEEP;
                BackgroundColor = Color.YELLOW;
            }
        }
        else if (State == BlockState.KEEP) {
            // 過線 -> State.FINISH
            if (Tool.IsOver(Body, GameMap.DetectLine.Body)) {
                Finish(Judgment.PERFECT);
            }

            // 長按失敗 -> State.DEAD
            if (!InputListener.IsKeyPress(HitKey) && Tool.IsCollision(Body, GameMap.DetectLine.Body)) {
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
        HitBody = Tool.GetPart(Body, HitRate);
    }
}
