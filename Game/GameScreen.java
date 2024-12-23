package Game;

import Tool.InputListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameScreen extends JPanel implements Runnable{
    public JFrame Windows;
    public boolean IsShowDetail = false;
    public boolean IsRunning = false;
    public float Target_FPS = 100;
    public float Target_UPS = 100;
    public float Current_FPS;
    public float Current_UPS;

    private double timePerFrame = 1000000000.0 / Target_FPS;
    private double timePerUpdate = 1000000000.0 / Target_UPS;
    private Thread gameThread;


    public GameScreen() {
        setPreferredSize(new Dimension(600, 400));
        setDoubleBuffered(true);

        InputListener input = new InputListener();
        addKeyListener(input);
        addMouseListener(input);
        addMouseMotionListener(input);
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

    public void setFPS(float fps) {
        Target_FPS = fps;
        timePerFrame = 1000000000.0 / Target_FPS;
    }

    public void setUPS(float ups) {
        Target_UPS = ups;
        timePerUpdate = 1000000000.0 / Target_UPS;
    }
    
    public void Start() {
        initial();

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        IsRunning = false;
    }
    
    public synchronized void quit() {
        stop();
        // gameThread.interrupt();

        Windows.setVisible(false);
        Windows.dispose();
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

        if (IsShowDetail) {
            g.setColor(Color.BLACK);
            char[] data = String.format("FPS : %f", Current_FPS).toCharArray();
            g.drawChars(data, 0, data.length, 10, 10);
            data = String.format("UPS : %f", Current_UPS).toCharArray();
            g.drawChars(data, 0, data.length, 10, 25);

            // paint input listener
            g.setColor(Color.BLUE);
            g.drawRect(getWidth() - 20, 10, 10, 100);
            g.fillRect(getWidth() - 20, 10 + (10 - InputListener.keep) * 10, 10, InputListener.keep * 10);
        }
    }

    @Override
    public void run() {
		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

		int frames = 0;
        int updates = 0;

        IsRunning = true;

		while (IsRunning) {

			// Render
            if (System.nanoTime() - lastFrame >= timePerFrame) {
                // repaint();
				paintImmediately(0, 0, getWidth(), getHeight());
				lastFrame = System.nanoTime();
				frames++;
			}

			// Update
            if (System.nanoTime() - lastUpdate >= timePerUpdate) {
                update((float) (System.nanoTime() - lastUpdate) / 1000000000f);
                lastUpdate = System.nanoTime();
                updates++;
            }

            // Reset
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
