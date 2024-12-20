package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public abstract class GameObject {
    public Rectangle Body;
    public Color BackgroundColor;
    public int Alpha;
    public BufferedImage Image;

    public abstract void Update(float delta);
    public abstract void paintComponent(Graphics g);
}
