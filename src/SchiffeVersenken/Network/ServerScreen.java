package SchiffeVersenken.Network;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class ServerScreen extends JFrame {
    private Server host;
    private JTextArea console;
    private Container cp;

    private int width;
    private int height;

    public ServerScreen() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) ((int) size.getWidth() - size.getWidth() / 5);
        height = (int) ((int) size.getHeight() - size.getHeight() / 5);

        try {
            host = new Server();
        } catch (IOException e) {
            System.out.println("Server error");
        }

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

    private void setTextConsole(){
        try {
            console.setText(host.getLocalHost());
            console.setFont(new Font("Times new Roman", Font.PLAIN, 20 ));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
