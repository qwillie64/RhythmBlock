package Effect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import Tool.Tool;


public class ParticularManager {
    public static List<Particular> collection = new LinkedList<>();
    
    public static void add(Particular p) {
        collection.add(p);
    }

    public static void create(Rectangle area, int count) {
        for (int i = 0; i < count; i++) {
            int rand_x = area.x + (int) (Math.random() * area.width) + 1;
            float rand_sy = (float) (Math.random() * 100f) + 30f;
            int rand_a = (int) (Math.random() * 200) + 50;
            Point center = new Point(rand_x, (int) area.getCenterY());
            Particular p = new Particular(center, new Point2D.Float(0, -rand_sy), Color.WHITE);
            p.TimeLimit = 2;
            p.color = Tool.Combine(Color.WHITE, rand_a);
            collection.add(p);
        }
        System.out.println("create");
    }
    
    public static void update(float delta) {
        for (Particular p : collection) {
            p.update(delta);
        }

        collection.removeIf(obj -> obj.IsEnd == true);
    }
    
    public static void draw(Graphics2D g) {
        for (Particular p : collection) {
            p.draw(g);
        }
    }
}
