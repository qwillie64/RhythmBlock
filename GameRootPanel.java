import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class GameRootPanel extends JPanel  {
    boolean IsRunning = false;
    float TargetFPS = 40;
    float GameTime = 0;

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
        IsRunning = true;

        while (IsRunning) {
            long currentTime = System.nanoTime();
            float lastUpdateLength = (float)(currentTime - lastUpdateTime) / 1000000000; // seconds
            lastUpdateTime = currentTime;
            float delta = lastUpdateLength / DELTA;

            // update game state
            Update(lastUpdateLength);

            // repaint all graphics
            paintImmediately(0, 0, getWidth(), getHeight());

            // update input
            // System.out.println(InputListener.ToString());
            InputListener.Refresh();

            time += lastUpdateLength;
            if (time >= timer) {
                System.out.println("FPS : " + delta * TargetFPS);
                time = 0;
            }

            try {
                long remaining = (long) ((lastUpdateTime - System.nanoTime() + DELTA * 1000000000) / 1000000);
                Thread.sleep(remaining);
            } catch (InterruptedException ex) {
            }
        }
    }

    protected void Update(float delta) {
        if (InputListener.IsKeyClick(KeyEvent.VK_A)) {
            GameObjectManager.Add(new Block(new Point(50, 0), 30, 32, 200));
        }
        if (InputListener.IsKeyClick(KeyEvent.VK_S)) {
            GameObjectManager.Add(new Block(new Point(150, 0), 30, 32, 200));
        }
        if (InputListener.IsKeyPress(KeyEvent.VK_D)) {
            GameObjectManager.Add(new Block(new Point(250, 0), 60, 32, 200));
        }
        if (InputListener.IsKeyClick(KeyEvent.VK_UP)) {
            TargetFPS = TargetFPS + 10;
        }
        if (InputListener.IsKeyClick(KeyEvent.VK_DOWN)) {
            TargetFPS = TargetFPS - 10;
        }

        GameObjectManager.Update(delta);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // paint background
        g2.setBackground(Color.GRAY);

        // paint ruler
        g.setColor(Color.RED);
        for (int i = 0; i < 10; i++) {
            g.drawLine(0, i * 30, getWidth(), i * 30);
        }

        // paint all object
        GameObjectManager.paintComponent(g);
    }
}
