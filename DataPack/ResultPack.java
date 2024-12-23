package DataPack;

import org.json.JSONObject;

public class ResultPack extends DataPack {
        private String clientName;
        private int clientId;
        private String mapName;
        private int score;

        public ResultPack(String clientName, int clientId, String mapName, int score) {
            this.clientName = clientName;
            this.clientId = clientId;
            this.mapName = mapName;
            this.score = score;

            data = new JSONObject();
            data.put("name", clientName);
            data.put("id", clientId);
            data.put("map", mapName);
            data.put("score", score);
        }

        public static ResultPack toPack(String data) {
            JSONObject object = new JSONObject(data);
            ResultPack pack = new ResultPack(object.getString("name"), object.getInt("id"), object.getString("map"), object.getInt("score"));

            return pack;
        }

    public String getClientName() {
        return clientName;
    }

    public int getClientId() {
        return clientId;
    }

    public String getMapName() {
        return mapName;
    }

    public int getScore() {
        return score;
    }
    }
