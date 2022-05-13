package SchiffeVersenken.Components;

import SchiffeVersenken.Fenster.GUIControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientPanel extends CustomPanel {

    private JTextField eingabeField;
    private JButton joinButton;


    public ClientPanel(int width, int height, GUIControl guiControl) {
        super(width, height, guiControl);
        this.setLayout(null);

        Font standardFont = new Font("Times new Roman", Font.PLAIN, width / 20);

        eingabeField = new JTextField("Host-IP hier eintragen");
        eingabeField.setSize(width / 2, height / 4);
        eingabeField.setLocation(width / 2 - eingabeField.getWidth() / 2, height / 2 - eingabeField.getHeight());
        eingabeField.setFont(standardFont);
        eingabeField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                eingabeField.setText("");
            }
        });
        eingabeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiControl.connectToServer(eingabeField.getText());
            }
        });
        this.add(eingabeField);

        joinButton = new JButton("Join");
        joinButton.setSize(width / 2, height / 4);
        joinButton.setLocation(width / 2 - joinButton.getWidth() / 2, height / 2 + joinButton.getHeight() / 2);
        joinButton.setBackground(Color.white);
        joinButton.setForeground(Color.BLACK);
        joinButton.setFont(standardFont);
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiControl.connectToServer(eingabeField.getText());
            }
        });
        this.add(joinButton);


    }
}
