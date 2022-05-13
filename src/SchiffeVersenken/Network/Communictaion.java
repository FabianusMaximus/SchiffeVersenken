package SchiffeVersenken.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;

public abstract class Communictaion {
    protected Socket socket;
    protected volatile Deque<String> messageStack = new ArrayDeque<>();
    protected volatile boolean online;

    public Communictaion(Socket socket) {
        this.socket = socket;
    }

    /**
     * Funktion die in einem Tread auf eine Nachricht wartet
     */
    public abstract void init();

    /**
     *
     */
    public abstract void verarbeitenStack();

    /**
     * Funktion, die es ermöglicht den Clients eine Nachricht zu senden
     *
     * @param message String der Nachricht die gesendet werden soll
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        message = message + "\n";
        socket.getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Schließt den Socket
     */
    protected void shutdown() {
        online = false;
        try {
            socket.close();
            online = false;
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
