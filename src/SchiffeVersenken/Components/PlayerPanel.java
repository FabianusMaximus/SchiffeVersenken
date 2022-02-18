package SchiffeVersenken.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerPanel extends CustomPanel {

    public PlayerPanel(int pWidth){
        this.width = pWidth;
        this.height = pWidth/2;
        this.setBackground(Color.BLUE);
        this.setLayout(new GridLayout(1,2,width/20,0));

        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(Color.GREEN);
        playerPanel.setLayout(new GridLayout(10,10,5,5));
        this.add(playerPanel);

        ShipPanel[][] playerCell;
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

        JPanel enemyPanel = new JPanel();
        enemyPanel.setBackground(Color.RED);
        enemyPanel.setLayout(new GridLayout(10,10,5,5));
        this.add(enemyPanel);

        ShipPanel[][] enemyCell;
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
        int height = 500;
        JFrame testFrame = new JFrame();
        testFrame.setSize(new Dimension(width,height));
        Container cp = testFrame.getContentPane();
        cp.setLayout(null);
        cp.setBackground(Color.black);
        PlayerPanel playerPanel = new PlayerPanel(width);
        //TODO
        playerPanel.setBounds(20,20,width,height/2);
        cp.add(playerPanel);
        testFrame.setLocationRelativeTo(null);
        testFrame.setVisible(true);
    }
}
