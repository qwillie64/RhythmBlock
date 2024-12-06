package Entity;

import Main.BlockState;
import Main.Judgment;
import Score.ScoreManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends GameObject {
    public float Speed;
    public int TimeMark;
    public int Duration;
    public int HitKey;
    public int Alpha;
    public BlockState State;

    public Block(Rectangle body, float speed, int hitKey, int timeMark, int duration) {
        this.Body = body;
        this.Speed = speed;
        this.HitKey = hitKey;
        this.TimeMark = timeMark;
        this.Duration = duration;
        this.Alpha = 255;
        this.BackgroundColor = Color.BLACK;
        this.State = BlockState.STAY;
    }

    public void Kill() {
        State = BlockState.DEAD;
        BackgroundColor = Color.RED;
        ScoreManager.AddScore(Judgment.MISS);
    }

    public void Finish(Judgment judgment) {
        State = BlockState.FINISH;
        BackgroundColor = Color.GREEN;
        ScoreManager.AddScore(judgment);
    }
    
    @Override
    public void paintComponent(Graphics g) {
    }

    @Override
    public void Update(float delta) {
    }
}
