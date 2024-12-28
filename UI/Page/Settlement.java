package UI.Page;

import Game.GameRoot;
import Score.ScoreManager;
import State.GameState;
import UI.EventType;
import UI.UiButton;
import UI.UiComponent;
import UI.UiLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;


public class Settlement {
  
    private final List<UiComponent> uiComponents = new LinkedList<>();
    private final GameRoot gameRoot;

    public Settlement(GameRoot root) {
        this.gameRoot = root;
        final int WIDTH = root.Windows.getWidth();
        final int HEIGHT = root.Windows.getHeight();

        UiLabel score_label = new UiLabel(0,0, 100, 50);
        score_label.setCenterPosition(WIDTH / 2, 4 * HEIGHT / 10);
        score_label.Text = String.format("Score : %d", ScoreManager.GetCurrentScore());
        score_label.Font = new Font("Serif", Font.BOLD, 30);
        score_label.BackGroundColor = new Color(50,50,50,0);
        uiComponents.add(score_label);
        
        UiButton confirm_button = new UiButton(0, 0, 100, 50);
        confirm_button.setCenterPosition(WIDTH / 2, 7 * HEIGHT / 10);
        confirm_button.Text = "Confirm";
        confirm_button.Font = new Font("Serif", Font.BOLD, 20);
        confirm_button.BackGroundColor = Color.GREEN;
        confirm_button.setEventListener(EventType.CLICK, () -> {
            confirm();
        });
        confirm_button.setEventListener(EventType.HOVER, () -> {
            confirm_button.BackGroundColor = Color.GREEN;
        });
        confirm_button.setEventListener(EventType.PRESS, () -> {
            confirm_button.BackGroundColor = new Color(50, 150, 50);
        });
        confirm_button.setEventListener(EventType.LEAVE, () -> {
            confirm_button.BackGroundColor = Color.GREEN.darker();
        });
        uiComponents.add(confirm_button);
    }
    
    public void confirm() {
        GameState.State = GameState.MENU;
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
