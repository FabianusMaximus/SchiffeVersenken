package SchiffeVersenken.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public abstract class Communication {
    protected Socket socket;
    protected volatile boolean online;

    public Communication(Socket socket) {
        this.socket = socket;
    }

    /**
     * Funktion die in einem Tread auf eine Nachricht wartet
     */
    public abstract void init();

    /**
     * Funktion, die es ermöglicht den Clients eine Nachricht zu senden
     *
     * @param message String der Nachricht die gesendet werden soll
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        message = message + "\n";
        socket.getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));
        System.out.println("raus: " + message);
    }

    /**
     * Schließt den Socket
     */
    public void shutdown() {
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
}
