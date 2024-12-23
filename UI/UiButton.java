package UI;

import Tool.InputListener;
import java.awt.Graphics;
import java.awt.Point;

public class UiButton extends UiComponent {

    public UiButton(int x, int y, int width, int height) {
        this.Position = new Point(x, y);
        this.Size = new Point(width, height);
        this.Image = null;
    }
    
    public UiButton(Point position, Point size) {
        this.Position = position;
        this.Size = size;
        this.Image = null;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(BackGroundColor);
        g.fillRect(Position.x, Position.y, Size.x, Size.y);
        g.drawImage(Image, Position.x, Position.y, null);
    }

    @Override
    public void update(float delta) {
        if (getBody().contains(InputListener.MousePoint)) {
            if (InputListener.IsMouseClick) {
                triggerEvent(EventType.CLICK);
            }
            else if (InputListener.IsMousePress) {
                triggerEvent(EventType.PRESS);
            }
            else {
                triggerEvent(EventType.HOVER);
            }
        }
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
