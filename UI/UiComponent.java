package UI;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;


public abstract class UiComponent {
    public Point Position;
    public Point Size;
    public Color BackGroundColor;
    public Color FontColor;
    public BufferedImage Image;
    public String Text;
    public Font Font;

    private final Map<EventType, Runnable> actions = new HashMap<>();
    
    public UiComponent() {
        this.Position = new Point(0, 0);
        this.Size = new Point(0, 0);
        this.BackGroundColor = new Color(0, 0, 0, 0);
        this.FontColor = Color.BLACK;
        this.Image = null;
        this.Text = "";
        this.Font = null;
    };

    public Rectangle getBody() {
        return new Rectangle(Position.x, Position.y, Size.x, Size.y);
    }

    public void setCenterPosition(Point position) {
        this.Position.x = position.x - Size.x / 2;
        this.Position.y = position.y - Size.y / 2;
    }

    public void setCenterPosition(int x, int y) {
        this.Position.x = x - Size.x / 2;
        this.Position.y = y - Size.y / 2;
    }
    
        public void setEventListener(EventType eventType, Runnable action) {
        actions.put(eventType, action);
    }

    public void removeEventListener(EventType eventType, Runnable action) {
        actions.remove(eventType);
    }

    protected void triggerEvent(EventType eventType) {
        Runnable action = actions.get(eventType);
        if (action != null) {
            action.run();
        }
    }

    public abstract void update(float delta);
    public abstract void draw(Graphics g);
    public abstract void show();
    public abstract void close();
}
