package UI;

import Tool.InputListener;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class UiButton extends UiComponent {
    public boolean IsClicked;

    private Map<EventType, Runnable> actions = new HashMap<>();

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

    }

    @Override
    public void close() {

    }
}
