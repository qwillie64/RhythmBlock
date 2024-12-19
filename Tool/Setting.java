package Tool;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Setting {

    public static float Speed = 200f;
    public static int Offset = 0;
    
    public static Map<Integer, Integer> KeySet = new HashMap<Integer, Integer>(){{
            put(0, KeyEvent.VK_A);
            put(1, KeyEvent.VK_S);
            put(2, KeyEvent.VK_D);
            put(3, KeyEvent.VK_F);
    }};
}
