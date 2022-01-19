package SchiffeVersenken.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    private ServerSocket serverSocket;
    private Socket[] socket;
    private PrintWriter[] pr;
    private String[] message;

    private String[] coolerZufälligerSpruch;


    public Server() throws IOException {
        socket = new Socket[2];
        pr = new PrintWriter[2];
        message = new String[2];
        coolerZufälligerSpruch = new String[]{
                "Wie kann ich ihnen helfen?",
                "Willkommen bei Virus.com",
                "Bitte tragen sie hier ihren Namen und ihre Kreditkartennummer ein \n" +
                        "-------------",
                "RAM wird runtergeladen... bitte warten",
                "Wir haben zur zeit leider geschlossen, kommen sie gestern wieder",
                "Wenn der Server nicht erreichbar ist, beschweren sie sich bitte bei \"NoahGerber100@gmail.com\""
        };
        serverSocket = new ServerSocket(5050);
        System.out.println("IpV4-Adresse: " + InetAddress.getLocalHost());

    }

    public String getLocalHost() throws UnknownHostException {
        return String.valueOf(InetAddress.getLocalHost());
    }

    public void connect(int index) throws IOException {
        socket[index] = serverSocket.accept();
        System.out.println("client connected");

    }

    public void sendMessage(int index, String pMessage) throws IOException {
        pr[index] = new PrintWriter(socket[index].getOutputStream());
        pr[index].println(pMessage);
        pr[index].flush();
    }

    public void receiveMessage(int index) throws IOException {
        InputStreamReader in = new InputStreamReader(socket[index].getInputStream());
        BufferedReader bf = new BufferedReader(in);

        message[index] = bf.readLine();
    }

    public void printMessage(int index) {
        System.out.println("client " + index + " : "+ message[index]);
    }

    public int nextIndex(int index){
        if (index < socket.length){
            index++;
        }else{
            index--;
        }
        return index;
    }

    public String getCoolerZufälligerSpruch() {
        return coolerZufälligerSpruch[(int) (Math.random() * 6)];
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        int index = 0;
        while (true) {
            server.connect(index);
            server.receiveMessage(index);
            server.sendMessage(index,server.getCoolerZufälligerSpruch());
            server.printMessage(index);
            index = server.nextIndex(index);
        }
    }

}
