package Score;

import Main.Judgment;
import java.util.HashMap;
import java.util.Map;

public class ScoreManager {

    private static Map<Judgment, Integer> ScoreList = new HashMap<>();
    private static int Score = 0;
    
    public static void Clear() {
        ScoreList.clear();
        Score = 0;
    }
    
    public static void SetUp(Judgment judgment, int score) {
        ScoreList.put(judgment, score);
    }

    public static void AddScore(Judgment judgment) {
        Score += ScoreList.get(judgment);
    }

    public static int GetCurrentScore() {
        return Score;
    }

    public static int GetRuleScore(Judgment judgment) {
        return ScoreList.get(judgment);
    }
}
