package SchiffeVersenken.Components;

import SchiffeVersenken.Fenster.GUIControl;

import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.GridLayout;

public class PlayPanel extends CustomPanel {

    private ShipPanel[][] playerCell;
    private ShipPanel[][] enemyCell;

    public PlayPanel(int width, int height, GUIControl guiControl) {
        super(width, height, guiControl);
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        JPanel basePanel = new JPanel();
        basePanel.setBounds((int) (width * 0.07), (int) ((width / 2) * 0.04), (int) (width * 0.85), (int) ((width / 2) * 0.85));
        basePanel.setLayout(new GridLayout(1, 2, this.width / 20, 0));
        this.add(basePanel);

        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(Color.GREEN);
        playerPanel.setLayout(new GridLayout(10, 10, 5, 5));
        basePanel.add(playerPanel);

        playerCell = new ShipPanel[10][10];
        int playid = 0;
        for (int i = 0; i < playerCell.length; i++) {
            for (int j = 0; j < playerCell[i].length; j++) {
                playerCell[i][j] = new ShipPanel(playid++);
                playerCell[i][j].setBackground(Color.ORANGE);
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
        enemyPanel.setLayout(new GridLayout(10, 10, 5, 5));
        basePanel.add(enemyPanel);

        enemyCell = new ShipPanel[10][10];
        int enemyid = 0;
        for (int i = 0; i < enemyCell.length; i++) {
            for (int j = 0; j < enemyCell[i].length; j++) {
                enemyCell[i][j] = new ShipPanel(enemyid++);
                enemyCell[i][j].setBackground(Color.ORANGE);
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

    public void setPlayerCell(ShipPanel[][] shipPanels) {
        playerCell = shipPanels;
    }

    public void updateShipStatus(ShipPanel[][] shipPanels){
        for (int i = 0; i < playerCell.length; i++) {
            for (int j = 0; j < playerCell[i].length; j++) {
                playerCell[i][j].setStatus(shipPanels[i][j].getStatus());
            }
        }
    }

    public ShipPanel[][] getPlayerCell() {
        return playerCell;
    }

    public void updatePlayerPanel() {
        for (int i = 0; i < playerCell.length; i++) {
            for (int j = 0; j < playerCell[0].length; j++) {
                if (playerCell[i][j].getStatus() == ShipPanel.Status.LOADED) {
                    playerCell[i][j].setBackground(Color.GREEN);
                    playerCell[i][j].repaint();
                } else if (playerCell[i][j].getStatus() == ShipPanel.Status.SUNKEN) {
                    playerCell[i][j].setBackground(Color.GRAY);
                    playerCell[i][j].repaint();
                }
            }
        }
    }

}
