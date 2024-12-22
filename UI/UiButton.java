package UI;

import Tool.InputListener;
import java.awt.Graphics;
import java.awt.Point;

public class UiButton extends UiComponent {

    public boolean IsClicked;
    public UiButton(Point position, Point size) {
        this.Position = position;
        this.Size = size;
        this.Image = null;
    }
    
    @Override
    public void update(Point mousePoint) {
        if (getBody().contains(mousePoint)) {
            if (InputListener.IsClicked) {
                IsClicked = true;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(BackGroundColor);
        g.fillRect(Position.x, Position.y, Size.x, Size.y);
        g.drawImage(Image, Position.x, Position.y, null);
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
