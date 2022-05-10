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
                shutdown();
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
                } else if (message.contains("sunken")) {
                    serverLogic.weiterleitenSunken(this, message);

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

}
