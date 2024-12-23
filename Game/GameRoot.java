package Game;

import Score.ScoreManager;
import State.GameState;
import State.Judgment;
import Tool.InputListener;
import Tool.MusicPlayer;
import UI.Menu;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;



public class GameRoot extends GameScreen{
    private GameMap gameMap;
    private Menu menu;

    public GameRoot() {
        super();

        Windows.setSize(800, 600);
    }


    @Override
    protected void initial() {
        super.initial();

        gameMap = new GameMap();
        gameMap.read("Assests//Daydreams.json", new Point(Windows.getBounds().width, Windows.getBounds().height));
        

        MusicPlayer.LoadSound("Assests//hit.wav", "hit");
        MusicPlayer.LoadSound("Assests//miss.wav", "miss");
        MusicPlayer.LoadMusic("Assests//Daydreams_edit.wav");
        MusicPlayer.SetStartPosition(10000);
        MusicPlayer.Play();

        ScoreManager.SetUp(Judgment.PERFECT, 100);
        ScoreManager.SetUp(Judgment.GOOD, 50);
        ScoreManager.SetUp(Judgment.MISS, -10);

        IsShowDetail = true;
        GameState.State = GameState.PLAYING;

        menu = new Menu();
    }
    
    
    @Override
    protected void update(float delta) {

        switch (GameState.State) {
            case PLAYING:
                gameMap.update(delta);
                break;
            case PAUSE:
                menu.update(delta);
                break;
            default:
                // ...
        }

        
        if (InputListener.IsKeyClick(KeyEvent.VK_ESCAPE)) {
            if (GameState.State == GameState.PAUSE) {
                MusicPlayer.Play();
                GameState.State = GameState.PLAYING;
            } else {
                MusicPlayer.pause();
                GameState.State = GameState.PAUSE;
            }
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
                menu.draw(g);
            
                g2.translate((Windows.getWidth() - gameMap.getSize().x) / 2, 0);
                gameMap.paintComponent(g);
                break;
            default:
                // ...
        }

        // paint score
        g.setColor(Color.BLACK);
        char[] data = String.format("Score : %d", ScoreManager.GetCurrentScore()).toCharArray();
        g.drawChars(data, 0, data.length, 10, 10);

        // paint input listener
        g2.translate(0, 0);
        g.setColor(Color.BLUE);
        g.drawRect(getWidth() - 20, 10, 10, 100);
        g.fillRect(getWidth() - 20, 10 + (10 - InputListener.keep) * 10, 10, InputListener.keep * 10);
    }
}
