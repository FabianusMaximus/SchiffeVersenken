package SchiffeVersenken.Network;

import SchiffeVersenken.Control;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;

    private ServerScreen serverScreen;

    private int index = 0;

    public Server() throws IOException {
        socket = new Socket();
        serverScreen = new ServerScreen(this);
        serverSocket = new ServerSocket(5050);
        System.out.println("IpV4-Adresse: " + InetAddress.getLocalHost());
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

    /**
     * Startet den Server und verbindet direkt einen Client damit
     * @param control
     * @throws IOException
     */
    public void startServer(Control control) throws IOException {
        serverScreen.addText("Waiting for other player");
        for (int i = 0; i < 2; i++) {
            socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(control.getGuiControl(),socket);
            control.addClientHandler(clientHandler);
            new Thread(clientHandler::init).start();
        }
        serverScreen.addText("Both Clients connected");
    }

    public void writeInConsole(String text){
        serverScreen.addText(text);
    }


}
