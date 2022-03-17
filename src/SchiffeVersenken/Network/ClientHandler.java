package SchiffeVersenken.Network;

import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.Fenster.ClientLogic;
import SchiffeVersenken.Fenster.GUIControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler {
    private GUIControl guiControl;
    private Socket socket;
    private boolean online;
    private PrintWriter pr;

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
                String message = receiveMessage();
                switch (message) {
                    case "ready" -> serverLogic.clientReady(this);
                    case "ping" -> System.out.println("ping");
                }
                if (message.contains("field")) {
                    serverLogic.setGameField(this, message);
                }

            } catch (IOException e) {
                e.printStackTrace();
                shutdown();
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

    public void goToPlayScreen() {
        guiControl.goToPlayScreen();
    }


}
