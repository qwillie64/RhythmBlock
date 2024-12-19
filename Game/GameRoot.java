package Game;

import Entity.DetectLine;
import Score.ScoreManager;
import State.Judgment;
import Tool.InputListener;
import Tool.MusicPlayer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class GameRoot extends Game{
    private GameMap gameMap;

    public GameRoot() {
        super();

        Windows.setSize(400, 600);
    }

    @Override
    protected void initial() {
        super.initial();

        gameMap = new GameMap();
        gameMap.SetDetectLine(new DetectLine(new Rectangle(0, 300, Windows.getWidth(), 2)));
        gameMap.read("Assests//Daydreams.json");

        MusicPlayer.LoadSound("Assests//hit.wav", "hit");
        MusicPlayer.LoadSound("Assests//miss.wav", "miss");
        MusicPlayer.LoadMusic("Assests//Daydreams_edit.wav");
        MusicPlayer.SetStartPosition(10000);
        MusicPlayer.Play();

        ScoreManager.SetUp(Judgment.PERFECT, 100);
        ScoreManager.SetUp(Judgment.GOOD, 50);
        ScoreManager.SetUp(Judgment.MISS, -10);
    }
    
    @Override
    protected void update(float delta) {
        gameMap.update(delta);

        super.update(delta);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // paint background


        // paint score
        char[] data = String.format("Score : %d", ScoreManager.GetCurrentScore()).toCharArray();
        g.drawChars(data, 0, data.length, 10, 10);

        // paint input listener
        g.setColor(Color.BLUE);
        g.drawRect(getWidth() - 20, 10, 10, 100);
        g.fillRect(getWidth() - 20, 10 + (10 - InputListener.keep) * 10, 10, InputListener.keep * 10);

        // paint all object
        gameMap.paintComponent(g);
    }
}
