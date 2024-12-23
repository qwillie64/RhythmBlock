package UI.Page;

import Game.GameRoot;
import UI.EventType;
import UI.UiButton;
import UI.UiComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;


public class Menu{
    
    private final List<UiComponent> uiComponents = new LinkedList<>();
    private final GameRoot gameRoot;

    public Menu(GameRoot root) {
        this.gameRoot = root;
        final int WIDTH = root.Windows.getWidth();
        final int HEIGHT = root.Windows.getHeight();

        UiButton single_play_button;
        single_play_button = new UiButton(0, 0, 100, 50);
        single_play_button.setCenterPosition(WIDTH / 2, 3 * HEIGHT / 10);
        single_play_button.BackGroundColor = Color.GREEN;
        single_play_button.setEventListener(EventType.CLICK, () -> {
            startSinglePlay();
        });
        uiComponents.add(single_play_button);

        UiButton multi_play_button;
        multi_play_button = new UiButton(0, 0, 100, 50);
        multi_play_button.setCenterPosition(WIDTH / 2, 4 * HEIGHT / 10);
        multi_play_button.BackGroundColor = Color.GRAY;
        multi_play_button.setEventListener(EventType.CLICK, () -> {
            startMultiPlay();
        });
        uiComponents.add(multi_play_button);

        UiButton quit_button;
        quit_button = new UiButton(0, 0, 100, 50);
        quit_button.setCenterPosition(WIDTH / 2, 5 * HEIGHT / 10);
        quit_button.BackGroundColor = Color.RED;
        quit_button.setEventListener(EventType.CLICK, () -> {
            quit();
        });
        uiComponents.add(quit_button);
    }
    
    public void startSinglePlay() {
        System.out.println("start single play");
        gameRoot.startGamePlay();
    }

    public void startMultiPlay() {
        System.out.println("start multi play");
    }

    public void quit() {
        System.out.println("exit game");
        gameRoot.quit();
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
