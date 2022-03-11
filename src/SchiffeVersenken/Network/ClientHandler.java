package SchiffeVersenken.Network;

import SchiffeVersenken.Components.ShipPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private boolean online, ready;
    private PrintWriter pr;

    private ShipPanel[][] gameField;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        online = true;

        gameField = new ShipPanel[10][10];
        int id = 0;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                gameField[i][j] = new ShipPanel(id);
                id++;
            }
        }
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
                    case "ready":
                        ready = true;
                        break;
                    case "ping":
                        break;
                }
                if (message.contains("field")) {
                    translateGamefield(message);
                    printGamefield();
                }

            } catch (IOException e) {
                e.printStackTrace();
                shutdown();
            }
        }
    }

    /**
     * Funktion, die es ermöglicht den Clients eine Nachricht zu senden
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

    /**
     * Baut aus dem String der Über das Netz geschickt wurde wieder ein normales Gamefield
     *
     * @param input Input-String des Sockets
     */
    public void translateGamefield(String input) {

        char[] field = new char[input.length() - 4];
        input.getChars(4, input.length(), field, 0);

        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameField[i][j].setBlocked(field[count] == '1');
                count++;
            }
        }
    }

    /**
     * Funktion zum Überprüfen, ob das gamefield auch richtig übersetzt wurde
     */
    public void printGamefield() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                System.out.print(gameField[i][j].isBlocked() + "\t");
            }
            System.out.println();
        }
    }

    /**
     * @return Boolean, ob der Spieler bereit ist
     */
    public boolean isReady() {
        return ready;
    }
}
