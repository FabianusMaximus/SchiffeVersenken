package SchiffeVersenken.Network;

import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.Fenster.ClientLogic;
import SchiffeVersenken.Fenster.GUIControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;

public class ClientHandler {
    private GUIControl guiControl;
    private Socket socket;
    private boolean online;
    private PrintWriter pr;
    private volatile Deque<String> messageStack = new ArrayDeque<>();

    private ServerLogic serverLogic;

    public ClientHandler(GUIControl guiControl, Socket socket) {
        this.guiControl = guiControl;
        this.socket = socket;
        online = true;


    }

    /**
     * Funktion zum Ausführen der Funktion des Servers
     */
    public void init() {
        System.out.println("Socket " + socket.getInetAddress().getHostName() + " connected");
        while (online) {
            try {
                messageStack.add(receiveMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    public void verarbeitenStack() {
        while (online) {
            if (!messageStack.isEmpty()) {
                String message = messageStack.pop();
                switch (message) {
                    case "ping" -> System.out.println("ping");
                }
                if (message.contains("field")) {
                    serverLogic.setGameField(this, message);
                    serverLogic.clientReady(this);
                } else if (message.contains("shot")) {
                    serverLogic.verarbeitenShot(message, this);
                }
            }
        }
    }

    /**
     * Funktion, die es ermöglicht den Clients eine Nachricht zu senden
     *
     * @param pMessage String der Nachricht die gesendet werden soll
     * @throws IOException
     */
    public void sendMessage(String pMessage) throws IOException {
        pr = new PrintWriter(socket.getOutputStream());
        pr.println(pMessage);
        pr.flush();
    }

    /**
     * Schließt den Socket
     */
    private void shutdown() {
        online = false;
        try {
            socket.close();
            System.out.println(socket.getInetAddress().getHostName() + " closed");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not close " + socket.getInetAddress().getHostName());
        }
    }

    /**
     * Ermöglicht es dem Client eine Nachricht zu erhalten
     *
     * @return String der Nachricht die empfangen wurde
     * @throws IOException
     */
    public String receiveMessage() throws IOException {
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        return bf.readLine();
    }

    public void setServerLogic(ServerLogic serverLogic) {
        this.serverLogic = serverLogic;
    }

    public void goToPlayScreen(boolean activePlayer) {
        try {
            sendMessage("bothready" + activePlayer);
            System.out.println("habe bothready gesendet");
        } catch (IOException e) {
            System.out.println("Nachricht will nicht raus");
        }
    }

    public void setActivePlayer(boolean activePlayer) {
        if (activePlayer) {
            try {
                sendMessage("yourTurn");
                System.out.println("habe yourTurn Gesendet");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                sendMessage("notYourTurn");
                System.out.println("habe notYourTurn Gesendet");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
