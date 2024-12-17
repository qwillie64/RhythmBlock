package Game;

import Tool.InputListener;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {
    public JFrame Windows;
    public boolean IsShowDetail = false;
    public boolean IsRunning = false;
    private float targetFPS = 60;
    private float detailTimer = 0.5f;
    private float currentFPS = 0;
    private float runningTime = 0;


    public Game(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setDoubleBuffered(true);

        addKeyListener(new InputListener());
        setFocusable(true);

        setUpWindow();
    }
    
    private void setUpWindow() {
        Windows = new JFrame("New Game");
        Windows.add(this);
        Windows.pack();
        Windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Windows.setResizable(false);
        Windows.setLocationRelativeTo(null);
        Windows.requestFocus();
        Windows.setVisible(true);
    }

    private void Run() {
        long lastUpdateTime = System.nanoTime();
        float time = 0;
        final float DELTA = 1f / targetFPS;

        while (IsRunning) {
            long currentTime = System.nanoTime();
            float lastUpdateLength = (float)(currentTime - lastUpdateTime) / 1000000000; // seconds
            lastUpdateTime = currentTime;
            float delta = lastUpdateLength / DELTA;

            // update game state
            update(lastUpdateLength);

            // repaint all graphics
            paintImmediately(0, 0, getWidth(), getHeight());

            // 定時更新運行資訊
            time += lastUpdateLength;
            if (time >= detailTimer) {
                currentFPS = delta * targetFPS;
                runningTime += lastUpdateLength;
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

    public void Start() {
        IsRunning = true;
        initial();
        Run();
    }

    public void Stop() {
        IsRunning = false;
    }
    
    protected void initial() {
        System.out.println("Game Init");
    }
    
    protected void update(float delta) {
        InputListener.Refresh();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public float getCurrentFPS() {
        return currentFPS;
    }

    public float getRunningTime() {
        return runningTime;
    }
}
