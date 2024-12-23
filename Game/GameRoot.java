package Game;

import State.GameState;
import Tool.InputListener;
import UI.Page.Menu;
import UI.Page.Settlement;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;



public class GameRoot extends GameScreen{
    private GameMap gameMap;
    private Menu menuPage;
    private Settlement settlementPage;

    public GameRoot() {
        super();

        Windows.setSize(800, 600);

        menuPage = new Menu(this);
        settlementPage = new Settlement(this);
    }


    @Override
    protected void initial() {
        super.initial();

        IsShowDetail = true;
        GameState.State = GameState.MENU;
    }
    
    
    @Override
    protected void update(float delta) {

        switch (GameState.State) {
            case PLAYING:
                // 遊玩中
                gameMap.update(delta);

                if (InputListener.IsKeyClick(KeyEvent.VK_ESCAPE)) {
                    gameMap.pause();
                    GameState.State = GameState.PAUSE;
                }

                if (gameMap.IsEnd) {
                    GameState.State = GameState.SETTLEMENT;
                }
                
                break;
            case PAUSE:
                // 觀看中
                gameMap.viewModeUpdate(delta);

                if (InputListener.IsKeyClick(KeyEvent.VK_ESCAPE)) {
                    gameMap.restart();
                    GameState.State = GameState.PLAYING;
                }
                
                break;
            case MENU:
                menuPage.update(delta);
                break;
            case SETTLEMENT:
                settlementPage.update(delta);
                break;
            default:
                // ...
        }

        super.update(delta);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        switch (GameState.State) {
            case PLAYING:
                g2.translate((Windows.getWidth() - gameMap.getSize().x) / 2, 0);
                gameMap.paintComponent(g);
                break;
            case PAUSE:
                g2.translate((Windows.getWidth() - gameMap.getSize().x) / 2, 0);
                gameMap.paintComponent(g);
                break;
            case MENU:
                menuPage.draw(g);
                break;
            case SETTLEMENT:
                settlementPage.draw(g);
                break;
            default:
                // ...
        }
    }

    public void startGamePlay() {
        gameMap = new GameMap();
        gameMap.load("Assests//Daydreams.json", new Point(Windows.getWidth(), Windows.getHeight()));
        gameMap.start();
        
        GameState.State = GameState.PLAYING;
    }
}
