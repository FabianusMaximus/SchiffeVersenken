package SchiffeVersenken.Network;

import SchiffeVersenken.Fenster.GUIControl;

import javax.swing.*;
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
                    case "gameOver" -> serverLogic.verarbeitenGameOver(this);
                }
                if (message.contains("field")) {
                    try{
                        serverLogic.setGameField(this, message);
                        serverLogic.clientReady(this);
                    }catch (NullPointerException e){
                        System.out.println("Jetzt wart halt a weng");
                    }
                } else if (message.contains("shot")) {
                    serverLogic.verarbeitenShot(this, message);
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
