package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class DetectLine extends GameObject{

    public DetectLine(Rectangle body) {
        this.Body = body;
        this.BackgroundColor = Color.GREEN;
    }
    
    public boolean IsCollision(Rectangle r) {
        return r.intersects(Body);
    }
    
    @Override
    public void Update(float delta) {

    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(BackgroundColor);
        g.fillRect(Body.x, Body.y, Body.width, Body.height);
    }
}
