package SchiffeVersenken.Network;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Communication {
    private ServerLogic serverLogic;

    public ClientHandler(Socket socket) {
        super(socket);
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
                    case "ping" -> System.out.println("ping");
                    case "gameOver" -> serverLogic.weiterleitenMessage(this, "win");
                }
                if (message.contains("field")) {
                    try {
                        serverLogic.setGameField(this, message);
                        serverLogic.clientReady(this);
                    } catch (NullPointerException e) {
                        System.out.println("Jetzt wart halt a weng");
                    }
                } else if (message.contains("shot")) {
                    serverLogic.verarbeitenShot(this, message);
                } else if (message.contains("sunken")) {
                    serverLogic.weiterleitenMessage(this, message);

                }
            } catch (IOException e) {
                shutdown();
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
