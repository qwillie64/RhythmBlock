package Entity;

import Main.BlockState;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends GameObject {
    public float Speed;
    public int TimeMark;
    public int Duration;
    public int HitKey;
    public BlockState State = BlockState.STAY;

    public Block(Rectangle body, float speed, int hitKey, int timeMark, int duration) {
        this.Body = body;
        this.Speed = speed;
        this.HitKey = hitKey;
        this.TimeMark = timeMark;
        this.Duration = duration;
        this.color = Color.BLACK;
    }

    @Override
    public void paintComponent(Graphics g) {
    }

    @Override
    public void Update(float delta) {
    }
}
