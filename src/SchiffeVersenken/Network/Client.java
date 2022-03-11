package SchiffeVersenken.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private InputStreamReader stream;
    private BufferedReader reader;
    private PrintWriter writer;
    private String message;

    public Client(String ip) throws IOException {
        clientSocket = new Socket(ip, 5050);
    }

    public void sendMessage(String pMessage) throws IOException {
        PrintWriter pr = new PrintWriter(clientSocket.getOutputStream());
        pr.println(pMessage);
        pr.flush();
    }

    public void receiveMessage() throws IOException {
        InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        message = bf.readLine();
    }

    public void printMessage(){
        System.out.println("Server: " + message);
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost");
        Client client1 = new Client("localhost");

        client.sendMessage("test");
        client1.sendMessage("test1");




    }
}
