package Server;


public class Main {
    public static void main(String[] args) {
        ServerWindows windows = new ServerWindows();
        Server server = new Server(windows);
        server.setPort(15000);
        server.open();
    }
}
