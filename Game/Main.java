package Game;

import Client.Client;
import DataPack.ResultPack;

public class Main {

    public static void main(String[] args) {
        // GameRoot newGame = new GameRoot();
        // newGame.Start();

        ResultPack data = new ResultPack("willie", 10101, "test map", 999);
        boolean isSuccess = Client.sendResult(data);
        System.out.println("Success : " + isSuccess);
    }
}