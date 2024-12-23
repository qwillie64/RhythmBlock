package UI;

import java.awt.Graphics;
import java.awt.Point;


public class UiLabel extends UiComponent {

    public UiLabel(String text, int x, int y, int width, int height) {
        this.Text = text;
        this.Position = new Point(x, y);
        this.Size = new Point(width, height);
        this.Image = null;
    }
    
    public UiLabel(String text, Point position, Point size) {
        this.Text = text;
        this.Position = position;
        this.Size = size;
        this.Image = null;
    }
    
    @Override
    public void update(float delta) {
        
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(BackGroundColor);
        g.fillRect(Position.x, Position.y, Size.x, Size.y);
        g.drawImage(Image, Position.x, Position.y, null);
        g.drawString(Text, Position.x, Position.y);
    }

    @Override
    public void show() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
