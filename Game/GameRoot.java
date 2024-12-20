package Game;

import Score.ScoreManager;
import State.GameState;
import State.Judgment;
import Tool.InputListener;
import Tool.MusicPlayer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

public class GameRoot extends Game{
    private GameMap gameMap;
    private GameState gameState;

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
        gameState = GameState.PLAYING;
    }
    
    @Override
    protected void update(float delta) {

        if (InputListener.IsKeyClick(KeyEvent.VK_ESCAPE)) {
            if (gameState == GameState.PAUSE) {
                MusicPlayer.Play();
                gameState = GameState.PLAYING;
            } else {
                MusicPlayer.pause();
                gameState = GameState.PAUSE;
            }
        }

        if (gameState == GameState.PLAYING) {
            gameMap.update(delta);
        }

        super.update(delta);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // paint all object
        g2.translate((Windows.getWidth() - gameMap.getSize().x) / 2, 0);
        gameMap.paintComponent(g);

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
