package Entity;

import Main.BlockState;
import Main.ObjectManager;
import Tool.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ClickBlock extends Block{
    
    public ClickBlock(Rectangle body, float speed, int hitKey, int timeMark, int duration) {
        super(body, speed, hitKey, timeMark, duration);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillRect(Body.x, Body.y, Body.width, Body.height);
    }

    @Override
    public void Update(float delta) {
        if (State == BlockState.ACTIVE) {
            // 點擊成功 -> State.FINISH
            if (InputListener.IsKeyClick(HitKey) && Tool.IsCollision(Body, ObjectManager.DetectLine.Body)) {
                State = BlockState.FINISH;
                color = Color.GREEN;
            }

            // 超線未點 -> Miss, State.DEAD
            if (Tool.IsOver(Body, ObjectManager.DetectLine.Body)) {
                State = BlockState.DEAD;
                color = Color.RED;
                return;
            }
        }

        
        Body.y = (int) (Body.y + Speed * delta);
    }
}
