package Entity;

import Animation.Animate;
import Effect.Effect;
import Game.GameMap;
import State.*;
import Tool.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class PressBlock extends Block {
    public Rectangle TailBody;
    private Color BorderColor;
    private float lightingBorder;
    private float tailP = 0.45f;

    public PressBlock(Rectangle body, int hitKey, int timeMark, int tailHeight) {
        super(body, hitKey, timeMark);
        this.TailBody = new Rectangle(body.x + (int)(body.width * tailP), body.y - tailHeight, body.width - (int)(body.width * tailP * 2f), tailHeight);
        this.BackgroundColor = Color.GRAY;

        BorderColor = Color.WHITE;
        lightingBorder = 8f;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Effect.lighting(g2, TailBody, lightingBorder, BorderColor);
        Effect.lighting(g2, Body, lightingBorder, BorderColor);
        g.setColor(Color.BLACK);
        g.fillRect(Body.x, Body.y, Body.width, Body.height);

        // g.setColor(Tool.Combine(BackgroundColor, Alpha));
        // g.fillRect(Body.x, Body.y, Body.width, Body.height);

        // g.setColor(Tool.Combine(BackgroundColor.brighter(), Alpha));
        // g.fillRect(TailBody.x, TailBody.y, TailBody.width, TailBody.height);
    }

    @Override
    public void Update(float delta) {
        current += delta;

        if (State == BlockState.ACTIVE) {
            // 超線未點 -> Miss, State.DEAD
            if (GameMap.DetectArea.IsOver(Body)) {
                Kill();
                BorderColor = Color.RED;
                setMovement(Body.getLocation(), new Point(Body.x, Body.y + 200), period);
                current = 0;
                return;
            }

            // 點擊成功 -> State.KEEP
            if (InputListener.IsKeyClick(HitKey) && GameMap.DetectArea.IsCollision(Body)) {
                State = BlockState.KEEP;
                BackgroundColor = Color.YELLOW;
                BorderColor = Color.YELLOW;
            }

            // 更新位置
            Body.y = Animate.EaseIn(start.y, end.y, current / period, 2.5f);
            // Body.y = Animate.Linear(start.y, end.y, current / period);
            TailBody.y = Body.y - TailBody.height;
        }
        else if (State == BlockState.KEEP) {
            // 過線 -> State.FINISH
            if (GameMap.DetectArea.IsOver(TailBody)) {
                Finish(Judgment.PERFECT);
            }

            // 長按失敗 -> State.DEAD
            if (!InputListener.IsKeyPress(HitKey) && GameMap.DetectArea.IsCollision(TailBody)) {
                Finish(Judgment.GOOD);
            }

            // 更新位置
            Body.y = Animate.EaseIn(start.y, end.y, current / period, 2.5f);
            // Body.y = Animate.Linear(start.y, end.y, current / period);
            TailBody.y = Body.y - TailBody.height;
        }
        else if (State == BlockState.DEAD) {
            Alpha -= 20;
            Body.y = Animate.Linear(start.y, end.y, current / period);
            TailBody.y = Body.y - TailBody.height;
            BorderColor = Tool.Combine(BorderColor, Alpha);
            lightingBorder += 0.95f;

            if (current / period > 1) {
                State = BlockState.FINISH;
            }
        }
    }
}
