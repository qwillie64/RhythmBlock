package Server;


public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.setPort(15000);
        server.open();
    }
}
