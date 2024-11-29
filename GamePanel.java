import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

public class GamePanel extends JPanel  {
    InputManager input = new InputManager();
    List<GameObject> GameObjectCollect = new LinkedList<>();
    boolean IsRunning = false;
    float TargetFPS = 30;

    public GamePanel(int width, int height) {

        setPreferredSize(new Dimension(width, height));
        setDoubleBuffered(true);

        addKeyListener(input);
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

            double delta = lastUpdateLength / ((double) OPTIMAL_TIME);

            // show input
            System.out.println(input.GetCurrentKeys());

            // update game state
            Update((float) delta);

            // repaint all graphics
            paintImmediately(0, 0, getWidth(), getHeight());

            try {
                long remaining = (long) ((lastUpdateTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
                Thread.sleep(remaining);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void Update(float delta) {

        for (GameObject gObject : GameObjectCollect) {
            gObject.Update(delta);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // pain background
        g.setColor(Color.BLACK);
        g.fillRect(50, 50, 50, 50);

        // paint all object
        for (GameObject gObject : GameObjectCollect) {
            gObject.paintComponent(g);
        }
    }
}
