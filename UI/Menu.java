package UI;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Menu {
    

    private Rectangle button;

    public Menu() {
        button = new Rectangle(50, 50, 100, 50);
    }


    public void draw(Graphics g){
        g.drawRect(button.x, button.y, button.width, button.height);
    }
}
