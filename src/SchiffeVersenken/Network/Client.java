package SchiffeVersenken.Network;

import SchiffeVersenken.Fenster.GUIControl;

import java.io.IOException;
import java.net.Socket;

public class Client extends Communication {
    private GUIControl guiControl;

    public Client(String ip, GUIControl guiControl) throws IOException {
        super(new Socket(ip, 5050));
        this.guiControl = guiControl;
        online = true;
    }

    public void init() {
        while (online) {
            try {
                String message = receiveMessage();
                System.out.println("Angekommene Nachricht: " + message);
                switch (message) {
                    case "bothreadyfalse" -> {
                        guiControl.goToPlayScreen();
                        guiControl.setActivePlayer(false);
                    }
                    case "yourTurn" -> guiControl.setActivePlayer(true);
                    case "notYourTurn" -> guiControl.setActivePlayer(false);
                    case "bothreadytrue" -> {
                        guiControl.goToPlayScreen();
                        guiControl.setActivePlayer(true);
                    }
                    case "treffer" -> {
                        guiControl.applyShot(true);
                    }
                    case "missed" -> {
                        guiControl.applyShot(false);
                        guiControl.flipActivePlayer();
                    }
                    case "win" -> guiControl.goToWinScreen(true);
                    case "ping" -> System.out.println("ping von: " + socket.getInetAddress());
                }
                if (message.contains("schuss")) {
                    guiControl.setEnemyShot(message);
                } else if (message.contains("sunken")) {
                    guiControl.verarbeitenSunken(message);
                }
            } catch (IOException e) {
                shutdown();
            }
        }
    }

}
