package Entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import Main.BlockState;
import Main.ObjectManager;
import Tool.*;

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
        if (State == BlockState.ACTIVE) {
            // 超線未點 -> Miss, State.DEAD
            if (Tool.IsOver(Body, ObjectManager.DetectLine.Body)) {
                State = BlockState.DEAD;
                return;
            }

            // 點擊成功 -> State.FINISH
            if (InputListener.IsKeyClick(HitKey) && Tool.IsCollision(Body, ObjectManager.DetectLine.Body)) {
            State = BlockState.FINISH;
            }
        }

        
        Body.y = (int) (Body.y + Speed * delta);
    }
}