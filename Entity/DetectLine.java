package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class DetectLine extends GameObject{

    public DetectLine(Rectangle body) {
        this.Body = body;
        this.BackgroundColor = Color.GREEN;
    }
    
    public static DetectLine Empty() {
        return new DetectLine(new Rectangle(0, 0, 0, 0));
    }
    
    public boolean IsCollision(Rectangle r) {
        return r.intersects(Body);
    }

    public boolean IsOver(Rectangle r) {
        return r.y > Body.y + Body.height;
    }

    @Override
    public void Update(float delta) {

    }

    @Override
    public void paintComponent(Graphics g) {
        try{
            g.setColor(BackgroundColor);
            g.drawLine(Body.x, Body.y, Body.x + Body.width, Body.y);
            g.drawLine(Body.x, Body.y + Body.height, Body.x + Body.width, Body.y + Body.height);
        }
        catch(Exception e){
            System.out.println(Body.toString());
        }
    }
}
