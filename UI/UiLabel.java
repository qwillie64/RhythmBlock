package UI;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;


public class UiLabel extends UiComponent {

    public UiLabel() {
        super();
    }
    
    public UiLabel(int x, int y, int width, int height) {
        super();
        this.Position = new Point(x, y);
        this.Size = new Point(width, height);
    }
    
    public UiLabel(Point position, Point size) {
        super();
        this.Position = position;
        this.Size = size;
    }
    
    @Override
    public void update(float delta) {
        
    }

    @Override
    public void draw(Graphics g) {
        // background
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
    public void show() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
