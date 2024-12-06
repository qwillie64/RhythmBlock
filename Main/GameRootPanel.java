package Main;

import Entity.*;
import Score.ScoreManager;
import Tool.InputListener;
import Tool.MusicPlayer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class GameRootPanel extends JPanel  {
    boolean IsRunning = false;
    float TargetFPS = 60;
    float BPM = 165;

    public GameRootPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setDoubleBuffered(true);

        addKeyListener(new InputListener());
        setFocusable(true);
    }

    public void Start() {
        IsRunning = true;
        Run();
    }

    public void Stop() {
        IsRunning = false;
    }
    
    public void Run() {
        long lastUpdateTime = System.nanoTime();
        float time = 0;
        final float timer = 0.3f;
        final float DELTA = 1f / TargetFPS;
        final float Bar = 60 / BPM;
        IsRunning = true;

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
        
        while (IsRunning) {
            long currentTime = System.nanoTime();
            float lastUpdateLength = (float)(currentTime - lastUpdateTime) / 1000000000; // seconds
            lastUpdateTime = currentTime;
            float delta = lastUpdateLength / DELTA;

            // update game state
            Update(lastUpdateLength);

            // repaint all graphics
            paintImmediately(0, 0, getWidth(), getHeight());

            // 定時更新運行資訊
            time += lastUpdateLength;
            if (time >= timer) {
                System.out.println("FPS : " + delta * TargetFPS);
                // System.out.println("Music : " + MusicPlayer.GetCurrentTime());
                time = 0;
            }

            try {
                long remaining = (long) ((lastUpdateTime - System.nanoTime() + DELTA * 1000000000) / 1000000);
                if (remaining <= 0) {
                    continue;
                }
                Thread.sleep(remaining);
            } catch (InterruptedException ex) {
            }
        }
    }

    protected void Update(float delta) {
        InputListener.Refresh();
        ObjectManager.Update(delta);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // paint background


        // paint score
        char[] data = String.format("Score : %d", ScoreManager.GetCurrentScore()).toCharArray();
        g.drawChars(data, 0, data.length, 10, 10);

        // paint all object
        ObjectManager.paintComponent(g);
    }
}
