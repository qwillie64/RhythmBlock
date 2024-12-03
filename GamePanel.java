import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

public class GamePanel extends JPanel  {
    List<GameObject> GameObjectCollect = new LinkedList<>();
    boolean IsRunning = false;
    float TargetFPS = 10;

    public GamePanel(int width, int height) {

        setPreferredSize(new Dimension(width, height));
        setDoubleBuffered(true);

        addKeyListener(new InputListener());
        setFocusable(true);
    }

    public void Run() {
        long lastUpdateTime = System.nanoTime();
        double OPTIMAL_TIME = 1000000000 / TargetFPS;
        IsRunning = true;

        while (IsRunning) {
            long currentTime = System.nanoTime();
            long lastUpdateLength = currentTime - lastUpdateTime;
            lastUpdateTime = currentTime;

            // delta
            double delta = lastUpdateLength / ((double) OPTIMAL_TIME);
            System.out.println(delta);

            // update game state
            Update((float) delta);

            // repaint all graphics
            paintImmediately(0, 0, getWidth(), getHeight());

            // update input
            // System.out.println(InputListener.ToString());
            InputListener.Refresh();

            try {
                long remaining = (long) ((lastUpdateTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
                Thread.sleep(remaining);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void Update(float delta) {
        if (InputListener.IsKeyClick(KeyEvent.VK_A)) {
            GameObjectCollect.add(new Block(new Point(50, 0), 10, 32, 200));
        }
        if (InputListener.IsKeyClick(KeyEvent.VK_S)) {
            GameObjectCollect.add(new Block(new Point(150, 0), 10, 32, 200));
        }
        
        for (GameObject gObject : GameObjectCollect) {
            gObject.Update(delta);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // pain background
        g2.setBackground(Color.GRAY);

        // paint all object
        g.setColor(Color.BLACK);
        for (GameObject gObject : GameObjectCollect) {
            gObject.paintComponent(g);
        }
    }
}
