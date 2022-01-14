package SchiffeVersenken.Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter writer;
    private String message;

    private String[] coolerZufälligerSpruch;


    public Server() throws IOException {
        coolerZufälligerSpruch = new String[]{
                "Wie kann ich ihnen helfen?",
                "Willkommen bei Virus.com",
                "Bitte tragen sie hier ihren Namen und ihre Kreditkartennummer ein \n" +
                        "-------------",
                "RAM wird runtergeladen... bitte warten",
                "Wir haben zur zeit leider geschlossen, kommen sie gestern wieder",
                "Wenn der Server nicht erreichbar ist, beschweren sie sich bitte bei \"NoahGerber100@gmail.com\""
        };
        serverSocket = new ServerSocket(5050);

    }

    public void connect() throws IOException {
        socket = serverSocket.accept();
        System.out.println("client connected");

    }

    public void sendMessage(String pMessage) throws IOException {
        PrintWriter pr = new PrintWriter(socket.getOutputStream());
        pr.println(pMessage);
        pr.flush();
    }

    public void receiveMessage() throws IOException {
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);

        message = bf.readLine();
    }

    public void printMessage() {
        System.out.println("client: " + message);
    }

    public String getCoolerZufälligerSpruch() {
        return coolerZufälligerSpruch[(int) (Math.random() * 6)];
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        while (true) {
            server.connect();
            server.receiveMessage();
            server.sendMessage(server.getCoolerZufälligerSpruch());
            server.printMessage();
        }
    }

}
