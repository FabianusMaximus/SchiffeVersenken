package SchiffeVersenken.Network;

import SchiffeVersenken.Fenster.GUIControl;
import java.io.IOException;
import java.net.Socket;

public class Client extends Communictaion {
    private GUIControl guiControl;

    public Client(String ip, GUIControl guiControl) throws IOException {
        super(new Socket(ip, 5050));
        this.guiControl = guiControl;
        online = true;
    }

    public void init() {
        while (online) {
            try {
                messageStack.add(receiveMessage());
            } catch (IOException e) {
                shutdown();
            }
        }
    }

    public void verarbeitenStack() {
        while (online) {
            if (!messageStack.isEmpty()) {
                String message = messageStack.pop();
                System.out.println("angekommene Nachricht: " + message);
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
                    case "ping" -> System.out.println("ping von: " + socket.getInetAddress());
                }
                if (message.contains("schuss")) {
                    guiControl.setEnemyShot(message);
                }else if (message.contains("sunken")){
                    guiControl.verarbeitenSunken(message);
                }
            }
        }
    }

}
