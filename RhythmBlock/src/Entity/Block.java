package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import Main.BlockState;

public class Block extends GameObject {
    public float Speed;
    public float Time;
    public int HitKey;
    public BlockState State = BlockState.ACTIVE;

    public Block(Rectangle body, float speed, int hitKey) {
        this.Body = body;
        this.Speed = speed;
        this.HitKey = hitKey;
        this.color = Color.BLACK;
    }

    @Override
    public void paintComponent(Graphics g) {
    }

    @Override
    public void Update(float delta) {
    }
}
