import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
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

    public void ReadMap(String path) {

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
        float bpm_time = 0;
        final float timer = 0.3f;
        final float DELTA = 1f / TargetFPS;
        final float Bar = 60 / BPM;
        IsRunning = true;

        ObjectManager.SetDetectLine(new DetectLine(new Rectangle(0, 300, getWidth(), 5)));

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

            bpm_time += lastUpdateLength;
            if (bpm_time >= 4 * Bar) {
                ObjectManager.AddBlock(new PressBlock(new Rectangle(0, 0, 50, 100), 200, KeyEvent.VK_A, 0.3f));
                ObjectManager.AddBlock(new ClickBlock(new Rectangle(80,0,50, 30), 200, KeyEvent.VK_S));
                bpm_time = 0;
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
        if (InputListener.IsKeyClick(KeyEvent.VK_UP)) {
            TargetFPS = TargetFPS + 10;
        }
        if (InputListener.IsKeyClick(KeyEvent.VK_DOWN)) {
            TargetFPS = TargetFPS - 10;
        }

        

        ObjectManager.Update(delta);
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
        ObjectManager.paintComponent(g);
    }
}
