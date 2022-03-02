package SchiffeVersenken.Components;

import SchiffeVersenken.Control;
import SchiffeVersenken.Fenster.GUIControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerPanel extends CustomPanel {

    private ShipPanel[][] playerCell;
    private ShipPanel[][] enemyCell;

    public PlayerPanel(int width, GUIControl guiControl){
        super(width, width/2, guiControl);
        this.setBackground(Color.WHITE);
        this.setLayout(new GridLayout(1,2, this.width /20,0));

        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(Color.GREEN);
        playerPanel.setLayout(new GridLayout(10,10,5,5));
        this.add(playerPanel);

        playerCell = new ShipPanel[10][10];
        int playerid = 0;
        for (int i = 0; i < playerCell.length; i++) {
            for (int j = 0; j < playerCell[i].length; j++) {
                playerCell[i][j] = new ShipPanel(playerid++);
                playerCell[i][j].setBackground(Color.black);
                int finalX = i;
                int finalY = j;
                playerCell[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                       //clickCell(finalX, finalY, selectedShip);
                    }
                });
                playerPanel.add(playerCell[i][j]);
            }
        }

        JPanel enemyPanel;
        enemyPanel= new JPanel();
        enemyPanel.setBackground(Color.RED);
        enemyPanel.setLayout(new GridLayout(10,10,5,5));
        this.add(enemyPanel);

        enemyCell = new ShipPanel[10][10];
        int enemyid = 0;
        for (int i = 0; i < playerCell.length; i++) {
            for (int j = 0; j < playerCell[i].length; j++) {
                enemyCell[i][j] = new ShipPanel(enemyid++);
                enemyCell[i][j].setBackground(Color.black);
                int finalX = i;
                int finalY = j;
                enemyCell[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //clickCell(finalX, finalY, selectedShip);
                    }
                });
                enemyPanel.add(enemyCell[i][j]);
            }
        }


    }

    public static void main(String[] args) {
        int width = 1000;
        JFrame testFrame = new JFrame();
        testFrame.setSize(new Dimension(width,width/2));
        Container cp = testFrame.getContentPane();
        cp.setLayout(null);
        cp.setBackground(Color.white);
        PlayerPanel playerPanel = new PlayerPanel(width, new GUIControl(new Control()));
        playerPanel.setBounds((int) (width*0.07), (int) ((width/2)*0.04), (int) (width*0.85), (int) ((width/2)*0.85));
        cp.add(playerPanel);
        testFrame.setLocationRelativeTo(null);
        testFrame.setVisible(true);
    }
}
