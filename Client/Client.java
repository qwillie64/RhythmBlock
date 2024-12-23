package Client;

import DataPack.DataPack;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static String hostname = "localhost";
    private static int port = 15000;

    public static boolean sendResult(DataPack data) {
        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to the server");

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            writer.println(data.toString());
            String response = reader.readLine();
            System.out.println("respone : " + response);
            if (response != null) {
                socket.close();
                return true;
            } else {
                socket.close();
                return false;
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
            return false;
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
            return false;
        }
    }
}
