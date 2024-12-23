package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port = 10000;
    private ServerWindows container;

    public Server(ServerWindows windows) {
        container = windows;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public void open() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected : " + socket.toString());

                new ClientHandler(socket, container).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
