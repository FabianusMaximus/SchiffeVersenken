package SchiffeVersenken.Components;

import SchiffeVersenken.Control;
import SchiffeVersenken.Fenster.GUIControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinPanel extends CustomPanel{

    private Font standardFont, buttonFont;
    JLabel winMessage;

    public WinPanel(int width, int height, GUIControl guiControl) {
        super(width, height, guiControl);

        this.setLayout(null);

        standardFont = new Font("Times new Roman", Font.PLAIN, height / 10);
        buttonFont = new Font("Times new Roman", Font.PLAIN, height/20);

        JPanel backgroundBox = new JPanel();
        backgroundBox.setBackground(Color.WHITE);
        backgroundBox.setSize(width,height);
        backgroundBox.setLocation(0,0);
        backgroundBox.setLayout(null);
        this.add(backgroundBox);

        JPanel balken = new JPanel();
        balken.setSize(width, height/3);
        balken.setLocation(0,height/7);
        balken.setBackground(Color.GRAY);
        balken.setLayout(null);
        backgroundBox.add(balken);

        JPanel winMessageBox = new JPanel();
        winMessageBox.setSize((int) (balken.getWidth()/1.2),balken.getHeight()/2);
        winMessageBox.setLocation((int) (balken.getWidth()/4), (int) (balken.getHeight()/3.5));
        winMessageBox.setBackground(Color.GRAY);
        winMessageBox.setLayout(new BorderLayout());
        balken.add(winMessageBox);

        winMessage = new JLabel("Win Message");
        winMessage.setFont(standardFont);
        winMessage.setForeground(Color.WHITE);
        winMessageBox.add(winMessage, BorderLayout.CENTER);

        JButton newGameButton = new JButton("Neues Spiel");
        newGameButton.setSize(width/5,height/5);
        newGameButton.setLocation(backgroundBox.getWidth()/4, (int) (backgroundBox.getHeight()/1.75));
        newGameButton.setFont(buttonFont);
        newGameButton.setBackground(Color.WHITE);
        backgroundBox.add(newGameButton);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Passiert noch nichts");
            }
        });

        JButton closeGameButton = new JButton("Spiel schließen");
        closeGameButton.setSize(width/5,height/5);
        closeGameButton.setLocation((int) (backgroundBox.getWidth()/1.75), (int) (backgroundBox.getHeight()/1.75));
        closeGameButton.setFont(buttonFont);
        closeGameButton.setBackground(Color.WHITE);
        backgroundBox.add(closeGameButton);
        closeGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    public void setWinMessage(Boolean pWin){
        if (pWin){
            winMessage.setText("DU HAST GEWONNEN");
            winMessage.setForeground(Color.GREEN);
        }else {
            winMessage.setText("DU HAST VERLOREN");
            winMessage.setForeground(Color.RED.darker());
        }
    }

    public static void main(String[] args) {
        JFrame testWindow2 = new JFrame();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) ((int) size.getWidth() - size.getWidth() / 5);
        int height = (int) ((int) size.getHeight() - size.getHeight() / 5);
        Container cp = testWindow2.getContentPane();
        cp.setLayout(null);

        testWindow2.setTitle("TestFenster");
        testWindow2.setSize(width, height);
        testWindow2.setResizable(false);
        testWindow2.setLocationRelativeTo(null);
        testWindow2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        WinPanel testPanel = new WinPanel(width, height, new GUIControl(new Control()));
        testPanel.setBounds(0, 0, width, height);
        cp.add(testPanel);

        testPanel.setWinMessage(true);

        testWindow2.setVisible(true);
    }

}
