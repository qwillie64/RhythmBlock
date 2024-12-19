package Game;

import Tool.InputListener;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable{
    public JFrame Windows;
    public boolean IsShowDetail = false;
    public boolean IsRunning = false;
    public final float Target_FPS = 120;
    public final float Target_UPS = 60;
    public float Current_FPS;
    public float Current_UPS;
    private float detailTimer = 0.5f;
    
    private float runningTime = 0;

    private Thread gameThread;


    public Game() {
        setPreferredSize(new Dimension(600, 400));
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
        final float DELTA = 1f / Target_FPS;

        IsRunning = true;

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
                Current_FPS = delta * Target_FPS;
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
        initial();

        gameThread = new Thread(this);
        gameThread.start();
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

    public float getRunningTime() {
        return runningTime;
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / Target_FPS;
		double timePerUpdate = 1000000000.0 / Target_UPS;

		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long lastTimeCheck = System.currentTimeMillis();

		int frames = 0;
        int updates = 0;

        IsRunning = true;

		while (IsRunning) {

			// Render
            if (System.nanoTime() - lastFrame >= timePerFrame) {
                repaint();
				// paintImmediately(0, 0, getWidth(), getHeight());
				lastFrame = System.nanoTime();
				frames++;
			}

			// Update
            if (System.nanoTime() - lastUpdate >= timePerUpdate) {
                update((float)(System.nanoTime() - lastUpdate) / 1000000000f);
                lastUpdate = System.nanoTime();
                updates++;
            }

            // Check game state
            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                Current_FPS = frames;
                Current_UPS = updates;

				frames = 0;
				updates = 0;
				lastTimeCheck = System.currentTimeMillis();
			}
		}
    }
}
