package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;


public class Menu{
    
    private final List<UiComponent> uiComponents = new LinkedList<>();

    public Menu() {
        UiButton test_button;
        test_button = new UiButton(50, 50, 100, 50);
        test_button.BackGroundColor = Color.GREEN;
        test_button.setEventListener(EventType.CLICK, () -> {
            System.out.println("click");
        });
        test_button.setEventListener(EventType.HOVER, () -> {
            test_button.BackGroundColor = new Color((int) (Math.random() * 255) + 1, (int) (Math.random() * 255) + 1,
                    (int) (Math.random() * 255) + 1);
        });
        uiComponents.add(test_button);
    }
    
    public void update(float delta) {
        for (UiComponent com : uiComponents) {
            com.update(delta);
        }
    }
    
    public void draw(Graphics g){
        for (UiComponent com : uiComponents) {
            com.draw(g);
        }
    }
}
