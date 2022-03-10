package SchiffeVersenken.Network;

import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.Control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;

    private ServerScreen serverScreen;


    private ShipPanel[][] gameField;

    private int index = 0;

    public Server() throws IOException {
        socket = new Socket();
        serverScreen = new ServerScreen(this);
        serverSocket = new ServerSocket(5050);
        System.out.println("IpV4-Adresse: " + InetAddress.getLocalHost());

        gameField = new ShipPanel[10][10];
        int id = 0;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                gameField[i][j] = new ShipPanel(id);
                id++;
            }
        }

    }

    public String getLocalHost() throws UnknownHostException {
        return String.valueOf(InetAddress.getLocalHost());
    }

    public void connect() throws IOException {
        socket = serverSocket.accept();
        System.out.println("client connected to socket " + index);
        this.index++;

    }

    private int nextIndex() {
        index++;
        if (index > 2) {
            index = 0;
        }
        return index;
    }

    public void startServer(Control control, ArrayList<ClientHandler> clients) throws IOException {
        control.setClient(new Client("localhost"));
        serverScreen.addText("Waiting for other player");
        for (int i = 0; i < 2; i++) {
            socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(socket);
            clients.add(clientHandler);
            new Thread(clientHandler::init).start();
        }
    }

    public void translateGamefield(String input) {

        char[] field = new char[input.length()];
        input.getChars(0, input.length(), field, 0);

        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameField[i][j].setBlocked(field[count] == '1');
                count++;
            }
        }
    }

    public void printGamefield() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                System.out.print(gameField[i][j].isBlocked() + "\t");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.translateGamefield("11111000000000000000111100000000000000001110000000000000000011000000000" +
                "00000000011000000000000000000");
        server.printGamefield();

    }
}
