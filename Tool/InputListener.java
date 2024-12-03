package Tool;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class InputListener implements KeyListener {

    private static final Set<Integer> pressedKeys = new HashSet<>();
    private static final Set<Integer> clickedKeys = new HashSet<>();
    private static final int KEEP = 5;
    private static int keep = 0;

    public static void Refresh() {
        keep++;

        if (keep >= KEEP) {
            clickedKeys.clear();
            keep = 0;
        }
    }
    
    public static Set<Integer> GetPressedKeys() {
        return pressedKeys;
    }
    
    public static Set<Integer> GetClickedKeys() {
        return clickedKeys;
    }

    public static boolean IsKeyClick(int key) {
        return clickedKeys.contains(key);
    }

    public static boolean IsKeyPress(int key) {
        return pressedKeys.contains(key);
    }

    public static String ToString() {
        return "Click : " + clickedKeys + ", Press : " + pressedKeys;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!pressedKeys.contains(e.getKeyCode())) {
            clickedKeys.add(e.getKeyCode());
        }

        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
}
