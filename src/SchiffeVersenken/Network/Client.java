package SchiffeVersenken.Network;

import SchiffeVersenken.Fenster.GUIControl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;

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
                e.printStackTrace();
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
                    case "bothreadytrue" -> {
                        guiControl.goToPlayScreen();
                        guiControl.setActivePlayer(true);
                    }
                    case "ping" -> System.out.println("ping");
                }
            }
        }
    }

}
