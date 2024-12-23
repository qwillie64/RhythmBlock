package UI.Page;

import Game.GameRoot;
import Score.ScoreManager;
import State.GameState;
import UI.EventType;
import UI.UiButton;
import UI.UiComponent;
import UI.UiLabel;
import java.awt.Color;
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

        String score = String.format("Score : %d", ScoreManager.GetCurrentScore());
        UiLabel score_label = new UiLabel(score, 0, 0, 100, 50);
        score_label.setCenterPosition(WIDTH / 2, 4 * HEIGHT / 10);
        score_label.BackGroundColor = Color.BLACK;
        uiComponents.add(score_label);
        
        UiButton confirm_button = new UiButton(0, 0, 100, 50);
        confirm_button.setCenterPosition(WIDTH / 2, 7 * HEIGHT / 10);
        confirm_button.BackGroundColor = Color.GREEN;
        confirm_button.setEventListener(EventType.CLICK, () -> {
            confirm();
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
