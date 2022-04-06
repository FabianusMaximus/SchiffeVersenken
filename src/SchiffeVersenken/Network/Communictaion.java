package SchiffeVersenken.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;

public abstract class Communictaion {
    protected Socket socket;
    protected volatile Deque<String> messageStack = new ArrayDeque<>();
    protected boolean online;

    public Communictaion(){

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
     * @param pMessage String der Nachricht die gesendet werden soll
     * @throws IOException
     */
    public void sendMessage(String pMessage) throws IOException {
        PrintWriter pr = new PrintWriter(socket.getOutputStream());
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
}
