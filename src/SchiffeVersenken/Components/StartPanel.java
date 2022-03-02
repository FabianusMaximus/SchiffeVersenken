package SchiffeVersenken.Components;

import SchiffeVersenken.Control;
import SchiffeVersenken.Fenster.GUIControl;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartPanel extends CustomPanel {
    private GUIControl guiControl;

    private JPanel buttonPanel;
    private JLabel title;
    private JPanel hostButton, joinButton;
    private JLabel hostLabel, joinLabel;

    public StartPanel(int width, int height, GUIControl guiControl) {
        super(width, height, guiControl);
        this.setLayout(null);

        Font standardFont = new Font("Times new Roman", Font.PLAIN, width / 20);

        //TODO Titelbild muss noch hinzugefügt werden

        title = new JLabel();
        title.setSize(width/2, height/4);
        title.setLocation(width/2-title.getWidth()/2,50);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        this.add(title);

        ImageIcon preLoad = new ImageIcon("src/SchiffeVersenken/img/Titel Image.png");
        ImageIcon imageIcon = new ImageIcon(preLoad.getImage()
                .getScaledInstance((int) (preLoad.getIconWidth()/2.5), (int) (preLoad.getIconHeight()/2.5), Image.SCALE_DEFAULT));
        title.setIcon(imageIcon);

        buttonPanel = new JPanel();
        buttonPanel.setSize(width / 2, height / 2);
        buttonPanel.setLocation(width / 2 - buttonPanel.getWidth() / 2,
                (int) (height / 2 - buttonPanel.getHeight() / 3));
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        this.add(buttonPanel);

        hostButton = new JPanel();
        hostButton.setLayout(new BorderLayout());
        hostButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                guiControl.clickHostGame();
            }
        });
        buttonPanel.add(hostButton);

        hostLabel = new JLabel("Host game");
        hostLabel.setBorder(new LineBorder(Color.black));
        hostLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hostLabel.setVerticalAlignment(SwingConstants.CENTER);
        hostLabel.setFont(standardFont);
        hostButton.add(hostLabel);


        joinButton = new JPanel();
        joinButton.setLayout(new BorderLayout());
        joinButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                guiControl.clickJoinGame();
            }
        });
        buttonPanel.add(joinButton);

        joinLabel = new JLabel("Join Game");
        joinLabel.setBorder(new LineBorder(Color.black));
        joinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        joinLabel.setVerticalAlignment(SwingConstants.CENTER);
        joinLabel.setFont(standardFont);
        joinButton.add(joinLabel);
    }

}
