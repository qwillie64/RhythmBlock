package Effect;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import Tool.Tool;


public class Effect {
    public static void lighting(Graphics2D g, Rectangle body, double border, Color color){
        Color c0 = color;
        Color c1 = Tool.Combine(color, 0);

        double x0 = body.x;
        double y0 = body.y;
        double x1 = body.x + body.width;
        double y1 = body.y + body.height;
        double w = body.width;
        double h = body.height;

        // Left
        g.setPaint(new GradientPaint(
            new Point2D.Double(x0, y0), c0,
            new Point2D.Double(x0 - border, y0), c1));
        g.fill(new Rectangle2D.Double(x0 - border, y0, border, h));

        // Right
        g.setPaint(new GradientPaint(
            new Point2D.Double(x1, y0), c0,
            new Point2D.Double(x1 + border, y0), c1));
        g.fill(new Rectangle2D.Double(x1, y0, border, h));

        // Top
        g.setPaint(new GradientPaint(
            new Point2D.Double(x0, y0), c0,
            new Point2D.Double(x0, y0 - border), c1));
        g.fill(new Rectangle2D.Double(x0, y0 - border, w, border));

        // Bottom
        g.setPaint(new GradientPaint(
            new Point2D.Double(x0, y1), c0,
            new Point2D.Double(x0, y1 + border), c1));
        g.fill(new Rectangle2D.Double(x0, y1, w, border));

        float fractions[] = new float[] { 0.0f, 1.0f };
        Color colors[] = new Color[] { c0, c1 };

        // Top Left
        g.setPaint(new RadialGradientPaint(
            new Rectangle2D.Double(x0 - border, y0 - border, border + border, border + border), 
            fractions, colors, CycleMethod.NO_CYCLE));
        g.fill(new Rectangle2D.Double(x0 - border, y0 - border, border, border));

        // Top Right
        g.setPaint(new RadialGradientPaint(
            new Rectangle2D.Double(x1 - border, y0 - border, border + border, border + border), 
            fractions, colors, CycleMethod.NO_CYCLE));
        g.fill(new Rectangle2D.Double(x1, y0 - border, border, border));

        // Bottom Left
        g.setPaint(new RadialGradientPaint(
            new Rectangle2D.Double(x0 - border, y1 - border, border + border, border + border), 
            fractions, colors, CycleMethod.NO_CYCLE));
        g.fill(new Rectangle2D.Double(x0 - border, y1, border, border));

        // Bottom Right
        g.setPaint(new RadialGradientPaint(
            new Rectangle2D.Double(x1 - border, y1 - border, border + border, border + border), 
            fractions, colors, CycleMethod.NO_CYCLE));
        g.fill(new Rectangle2D.Double(x1, y1, border, border));
    }
}
