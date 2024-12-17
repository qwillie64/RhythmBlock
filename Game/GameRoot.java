package Game;

import Entity.DetectLine;
import Main.Judgment;
import Main.MapDetail;
import Main.ObjectManager;
import Score.ScoreManager;
import Tool.InputListener;
import Tool.MusicPlayer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class GameRoot extends Game{
    
    public GameRoot() {
        super(600, 400);
    }

    @Override
    protected void initial() {
        super.initial();

        ObjectManager.SetDetectLine(new DetectLine(new Rectangle(0, 300, getWidth(), 5)));

        MapDetail md = new MapDetail();
        md.Read("Assests//GlitchyGlass.csv");

        MusicPlayer.LoadSound("Assests//hit.wav", "hit");
        MusicPlayer.LoadSound("Assests//miss.wav", "miss");
        MusicPlayer.LoadMusic("Assests//GlitchyGlass.wav");
        MusicPlayer.SetStartPosition(20000);
        MusicPlayer.Play();

        ScoreManager.SetUp(Judgment.PERFECT, 100);
        ScoreManager.SetUp(Judgment.GOOD, 50);
        ScoreManager.SetUp(Judgment.MISS, -10);
    }
    
    @Override
    protected void update(float delta) {
        super.update(delta);

        ObjectManager.Update(delta);
        InputListener.Refresh();
        
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

        ObjectManager.paintComponent(g);
        
        
    }
}
