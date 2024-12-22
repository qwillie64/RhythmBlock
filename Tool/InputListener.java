package Tool;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

public class InputListener implements KeyListener, MouseListener{

    private static final Set<Integer> pressedKeys = new HashSet<>();
    private static final Set<Integer> clickedKeys = new HashSet<>();
    private static final int KEEP = 10;
    public static int keep = 0;

    public static boolean IsClicked;
    public static boolean IsPressed;

    public static void Refresh() {
        clickedKeys.clear();
        if (keep > 0) {
            keep--;
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
            keep = KEEP;
        }

        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        IsClicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        IsPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        IsClicked = false;
        IsPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
