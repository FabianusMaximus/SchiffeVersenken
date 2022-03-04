package SchiffeVersenken.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private Socket[] socket;
    private PrintWriter[] pr;
    private String[] message;

    private String[] coolerZufälligerSpruch;

    private int index = 0;

    public Server() throws IOException {
        socket = new Socket[2];
        pr = new PrintWriter[2];
        message = new String[2];
        serverSocket = new ServerSocket(5050);
        System.out.println("IpV4-Adresse: " + InetAddress.getLocalHost());

    }

    public String getLocalHost() throws UnknownHostException {
        return String.valueOf(InetAddress.getLocalHost());
    }

    public void connect() throws IOException {
        socket[index] = serverSocket.accept();
        Thread myThread = new Thread(this);
        myThread.start();
        System.out.println("client connected");

    }

    public void sendMessage(String pMessage) throws IOException {
        pr[index] = new PrintWriter(socket[0].getOutputStream());
        pr[index].println(pMessage);
        pr[index].flush();
    }

    public void receiveMessage(int index) throws IOException {
        InputStreamReader in = new InputStreamReader(socket[index].getInputStream());
        BufferedReader bf = new BufferedReader(in);

        message[index] = bf.readLine();
    }

    public void printMessage(int index) {
        System.out.println("client " + index + " : " + message[index]);
    }

    public void start() throws IOException {
        connect();
    }

    @Override
    public void run() {
        while (true) {
            try {
                receiveMessage(index);
                printMessage(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Server s = new Server();
        s.start();
    }
}
