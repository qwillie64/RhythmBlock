package DataPack;

import org.json.JSONObject;


public abstract class DataPack {
    protected JSONObject data;
    
    @Override
    public String toString() {
        return data.toString();
    }
}
