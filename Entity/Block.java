package Entity;

import Score.ScoreManager;
import State.BlockState;
import State.Judgment;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;


public class Block extends GameObject {
    public int TimeMark;
    public int HitKey;
    public BlockState State;

    public Point start;
    public Point end;
    public float period;
    public float current;

    public Block(Rectangle body, int hitKey, int timeMark) {
        this.Body = body;
        this.HitKey = hitKey;
        this.TimeMark = timeMark;
        this.Alpha = 255;
        this.BackgroundColor = Color.BLACK;
        this.State = BlockState.STAY;
    }

    public void setMovement(Point start, Point end, float period){
        this.start = start;
        this.end = end;
        this.period = period;
        this.current = 0;
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
