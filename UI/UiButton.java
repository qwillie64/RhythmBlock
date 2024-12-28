package UI;

import Tool.InputListener;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

public class UiButton extends UiComponent {
    private boolean isHover;

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

        // text
        g.setColor(FontColor);
        g.setFont(Font);
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(Text);
        int textHeight = metrics.getHeight();
        int textAscent = metrics.getAscent();  
        int textX = Position.x + (Size.x - textWidth) / 2;
        int textY = Position.y + (Size.y - textHeight) / 2 + textAscent;
        g.drawString(Text, textX, textY);
    }

    @Override
    public void update(float delta) {
        if (getBody().contains(InputListener.MousePoint)) {
            if (InputListener.IsMouseClick) {
                triggerEvent(EventType.CLICK);
            } else if (InputListener.IsMousePress) {
                triggerEvent(EventType.PRESS);
            } else {
                triggerEvent(EventType.HOVER);
                isHover = true;
            }
        }
        else {
            if (isHover) {
                triggerEvent(EventType.LEAVE);
                isHover = false;
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
