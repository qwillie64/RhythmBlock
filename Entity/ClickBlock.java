package Entity;

import Animation.Animate;
import Effect.Effect;
import Game.GameMap;
import State.BlockState;
import State.Judgment;
import Tool.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class ClickBlock extends Block {
    private Color BorderColor;
    private float lightingBorder;

    public ClickBlock(Rectangle body , int hitKey, int timeMark) {
        super(body, hitKey, timeMark);

        BorderColor = Color.WHITE;
        lightingBorder = 8f;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Effect.lighting(g2, Body, lightingBorder, BorderColor);
        // Point2D center = new Point2D.Float(Body.x, Body.y);
        // float[] dist = {0.2f, 1.0f};
        // Color[] color = {Color.RED, new Color(0, 0, 0, 255)};
        // GradientPaint
        // RadialGradientPaint p = new RadialGradientPaint(, 50, dist, color);

        // g2.setPaint(p);
        // g.setColor(Tool.Combine(BackgroundColor, Alpha));
        // g2.fillRect(Body.x, Body.y, Body.width, Body.height);
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
                BorderColor = Color.RED;
                setMovement(Body.getLocation(), new Point(Body.x, Body.y + 200), period);
                current = 0;
                return;
            }

            // 更新位置
            Body.y = Animate.EaseIn(start.y, end.y, current / period, 2.5f);
            // Body.y = Animate.Linear(start.y, end.y, current / period);
        } else if (State == BlockState.DEAD) {
            Alpha -= 20;
            Body.y = Animate.Linear(start.y, end.y, current / period);
            BorderColor = Tool.Combine(BorderColor, Alpha);
            lightingBorder += 0.95f;

            if (current / period > 1) {
                State = BlockState.FINISH;
            }
        }
    }
}
