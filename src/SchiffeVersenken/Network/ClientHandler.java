package SchiffeVersenken.Network;

import SchiffeVersenken.Fenster.GUIControl;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Communictaion {

    private GUIControl guiControl;
    private ServerLogic serverLogic;

    public ClientHandler(GUIControl guiControl, Socket socket) {
        super(socket);
        this.guiControl = guiControl;
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
                }
            }
        }
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
