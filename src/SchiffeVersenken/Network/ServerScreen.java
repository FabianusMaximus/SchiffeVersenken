package SchiffeVersenken.Network;

import SchiffeVersenken.Control;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class ServerScreen extends JFrame {
    private JTextArea console;
    private Container cp;

    private Server server;

    private int width;
    private int height;

    public ServerScreen(Server server) {
        this.server = server;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) ((int) size.getWidth() / 2 - size.getWidth() / 5);
        height = (int) ((int) size.getHeight() / 2 - size.getHeight() / 5);

        cp = this.getContentPane();
        cp.setLayout(null);

        console = new JTextArea();
        console.setBounds(20, 20, width - 40, height - 40);
        console.setEditable(false);
        setTextConsole();
        cp.add(console);

        setTitle("Schiffeversenken - ServerConsole");
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    private void setTextConsole() {
        try {
            console.setText(server.getLocalHost() + "\n");
            console.setFont(new Font("Times new Roman", Font.PLAIN, 20));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void addText(String text) {
        console.append(text + "\n");
    }
}
