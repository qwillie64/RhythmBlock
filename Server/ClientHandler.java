package Server;

import DataPack.ResultPack;
import java.io.*;
import java.net.*;

class ClientHandler extends Thread {
    private Socket socket;
    private ServerWindows container;

    public ClientHandler(Socket socket, ServerWindows windows) {
        this.socket = socket;
        this.container = windows;
    }

    @Override
    public void run() {
        try (InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true)) {
            String message = reader.readLine();
            System.out.println("Received: " + message);
            ResultPack data = ResultPack.toPack(message);
            container.addResult(data);
            writer.println("ok");
            socket.close();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
