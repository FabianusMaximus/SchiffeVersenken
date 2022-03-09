package SchiffeVersenken.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private boolean online, ready;
    private PrintWriter pr;



    public ClientHandler(Socket socket) {
        this.socket = socket;
        online = true;
    }

    public void init() {
        System.out.println("Socket " + socket.getInetAddress().getHostName() + " connected");

        while(online)
        {
            try {
                String message = receiveMessage();
                switch (message)
                {
                    case "ready":
                        ready = true;
                        break;
                    case "ping":
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
                shutdown();
            }
        }
    }

    public void sendMessage(String pMessage) throws IOException {
        pr = new PrintWriter(socket.getOutputStream());
        pr.println(pMessage);
        pr.flush();
    }

    private void shutdown()
    {
        online = false;
        try {
            socket.close();
            System.out.println(socket.getInetAddress().getHostName() + " closed");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not close " + socket.getInetAddress().getHostName());
        }
    }

    public String receiveMessage() throws IOException {
        InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        return bf.readLine();
    }

    public boolean isReady() {
        return ready;
    }
}
