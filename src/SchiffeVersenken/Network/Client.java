package SchiffeVersenken.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clienSocket;
    private InputStreamReader stream;
    private BufferedReader reader;
    private PrintWriter writer;
    private String message;

    public Client() throws IOException {
        clienSocket = new Socket("192.168.178.33", 5050);
    }

    public void sendMessage(String pMessage) throws IOException {
        PrintWriter pr = new PrintWriter(clienSocket.getOutputStream());
        pr.println(pMessage);
        pr.flush();


    }

    public void receiveMessage() throws IOException {
        InputStreamReader in = new InputStreamReader(clienSocket.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        message = bf.readLine();

    }

    public void printMessage(){
        System.out.println("Server: " + message);
    }

    public static void main(String[] args) throws IOException {
        Client client0 = new Client();
        Client client1 = new Client();

        client0.sendMessage("hallo ich möchte bitte spielen");
        client0.receiveMessage();
        client0.printMessage();

        client1.sendMessage("ja, ich bitte auch");
        client1.receiveMessage();
        client1.printMessage();




    }
}
