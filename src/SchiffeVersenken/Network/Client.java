package SchiffeVersenken.Network;

import SchiffeVersenken.Fenster.GUIControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private GUIControl guiControl;
    private InputStreamReader stream;
    private BufferedReader reader;
    private PrintWriter writer;
    private String message;

    private boolean online;

    public Client(String ip, GUIControl guiControl) throws IOException {
        this.guiControl = guiControl;
        clientSocket = new Socket(ip, 5050);
        online = true;
    }

    public void init() {
        while (online) {
            try {
                String message = receiveMessage();
                System.out.println("angekommene Nachricht: " + message);
                switch (message) {
                    case "bothready" -> {
                        guiControl.goToPlayScreen();
                    }
                    case "ping" -> System.out.println("ping");
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String pMessage) throws IOException {
        PrintWriter pr = new PrintWriter(clientSocket.getOutputStream());
        pr.println(pMessage);
        pr.flush();
    }

    public String receiveMessage() throws IOException {
        InputStreamReader in = new InputStreamReader(clientSocket.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        return bf.readLine();
    }

    public void printMessage() {
        System.out.println("Server: " + message);
    }
}
