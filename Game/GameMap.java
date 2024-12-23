package Game;

import org.json.*;

import Entity.Block;
import Entity.ClickBlock;
import Entity.DetectLine;
import Entity.PressBlock;
import Score.ScoreManager;
import State.BlockState;
import State.Judgment;
import Tool.InputListener;
import Tool.SoundManager;
import Tool.Setting;
import Tool.SoundManager.SoundType;
import java.awt.Color;
import javafx.scene.paint.Color;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;


public class GameMap {
    public enum GameMode {
        PLAYING, VIEW
    }

    private float bpm;
    private int start;
    private int end = 20099000;
    private String name;
    private int musicId;
    private float volume = 0f;

    private int time_d;
    private Point size;

    public boolean IsEnd;
    public final int CHANNEL_WIDTH = 100;

    private Thread opening;
    private Thread closing;

    public static List<Block> ActiveBlockCollection;
    public static List<Block> StayBlockCollection;
    public static DetectLine DetectArea;
    public static GameMode GameMode;

    
    public GameMap() {
        size = new Point(0, 0);
        ActiveBlockCollection = new LinkedList<>();
        StayBlockCollection = new LinkedList<>();
        DetectArea = DetectLine.Empty();
    }
    
    public void load(String path, Point screenSize) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("Assests/Daydreams.json")));
            JSONObject jsonObject = new JSONObject(content);

            // read data
            bpm = jsonObject.getFloat("bpm");
            start = jsonObject.getInt("start");
            name = jsonObject.getString("name");
            JSONArray blocks = jsonObject.getJSONArray("blocks");

            int block_h = 20;
            int block_y = -block_h;

            // detect
            DetectArea = new DetectLine(new Rectangle(0, 2 * screenSize.y / 3, 4 * CHANNEL_WIDTH, block_h));

            time_d = (int) ((DetectArea.Body.y - block_y) * 1000000f / Setting.getSpeed());

            //blocks
            for (int i = 0; i < blocks.length(); i++) {
                JSONObject block = blocks.getJSONObject(i);
                int channel = block.getInt("channel");
                int b_start = block.getInt("start");
                int type = block.getInt("type");
                int period = block.getInt("period");

                Rectangle body = new Rectangle(channel * CHANNEL_WIDTH + 2, block_y, CHANNEL_WIDTH - 4, block_h);
                switch (type) {
                    case 0:
                        ClickBlock cb = new ClickBlock(body, Setting.KeySet.get(channel), b_start);
                        cb.setMovement(body.getLocation(), DetectArea.Body.getLocation(), time_d / 1000000f);
                        StayBlockCollection.add(cb);
                        break;

                    case 1:
                        PressBlock pb = new PressBlock(body, Setting.KeySet.get(channel), b_start,
                                (int) (Setting.getSpeed() * period / 1000000f));
                        pb.setMovement(body.getLocation(), DetectArea.Body.getLocation(), time_d / 1000000f);
                        StayBlockCollection.add(pb);
                        break;

                    default:
                        throw new AssertionError();
                }
            }

            loadScore();
            loadMusic();

            this.size = new Point(4 * CHANNEL_WIDTH, screenSize.y);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void loadScore() {
        ScoreManager.SetUp(Judgment.PERFECT, 100);
        ScoreManager.SetUp(Judgment.GOOD, 50);
        ScoreManager.SetUp(Judgment.MISS, -10);
    }
    
    public void loadMusic() {
        SoundManager.load("Assests//hit.wav", "hit", SoundType.SOUND);
        SoundManager.load("Assests//miss.wav", "miss", SoundType.SOUND);

        SoundManager.load("Assests//Daydreams_edit.wav", "Daydreams_edit", SoundType.MUSIC);
        musicId = SoundManager.prepare("Daydreams_edit", start, SoundType.MUSIC);

        opening = new Thread(() -> {
            System.out.println("Game Opening");
            while (volume < 1f) {
                volume += 0.03f;
                SoundManager.setVolume(musicId, volume);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            volume = 1f;
            SoundManager.setVolume(musicId, volume);
            System.out.println("Game Start");
        });

        closing = new Thread(() -> {
            System.out.println("Game Closing");
            while (volume > 0) {
                volume -= 0.04f;
                SoundManager.setVolume(musicId, volume);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            volume = 0f;
            SoundManager.setVolume(musicId, volume);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            IsEnd = true;
            System.out.println("Game Finish");
        });
    }

    public void start() {
        SoundManager.setVolume(musicId, volume);
        SoundManager.play(musicId);
        opening.start();
    }

    public void restart() {
        SoundManager.play(musicId);
    }
    
    public void pause() {
        SoundManager.pause(musicId);
    }
    
    public Point getSize() {
        return size;
    }

    public void update(float delta) {
        for (Block block : ActiveBlockCollection) {
            block.Update(delta);
        }

        final int current = SoundManager.getCurrentTime(musicId);
        for (Block block : StayBlockCollection) {
            int off = current - block.TimeMark + time_d;
            if (off >= 0) {
                block.current += off / 1000000f;
                block.State = BlockState.ACTIVE;
                ActiveBlockCollection.add(block);
            }
        }

        ActiveBlockCollection.removeIf(obj -> obj.State == BlockState.FINISH);
        StayBlockCollection.removeIf(obj -> obj.State == BlockState.ACTIVE);

        if (current >= end) {
            if (IsEnd) {
                return;
            }
            if (!closing.isAlive()) {
                closing.start();
            }
        }
    }
    
    public void viewModeUpdate(float delta) {
        for (Block block : ActiveBlockCollection) {
            if (block.Body.contains(InputListener.MousePoint)) {
                
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        for (Block block : ActiveBlockCollection) {
            block.paintComponent(g);
        }

        DetectArea.paintComponent(g);

        // paint score
        g.setColor(Color.BLACK);
        char[] data = String.format("Score : %d", ScoreManager.GetCurrentScore()).toCharArray();
        g.drawChars(data, 0, data.length, 10, 10);
    }
}