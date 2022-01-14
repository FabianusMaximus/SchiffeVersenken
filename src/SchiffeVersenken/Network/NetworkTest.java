package SchiffeVersenken.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkTest {
    private Socket chatSocket;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private PrintWriter writer;

    public NetworkTest() {

    }

    public void createSocket() {
        try {
            chatSocket = new Socket("172.0.0.1", 5000);
            inputStreamReader = new InputStreamReader(chatSocket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            String message = bufferedReader.readLine();
            writer = new PrintWriter(chatSocket.getOutputStream());
            writer.println("Hallo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NetworkTest nt = new NetworkTest();
        nt.createSocket();
    }
}
