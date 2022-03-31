package SchiffeVersenken.Components;

import SchiffeVersenken.Control;
import SchiffeVersenken.Fenster.GUIControl;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        JPanel playerColorPanel = new JPanel();
        playerColorPanel.setBackground(Color.GREEN);
        playerColorPanel.setLayout(null);
        basePanel.add(playerColorPanel);

        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(Color.blue);
        playerPanel.setBounds((int) (height*0.006), (int) (height*0.025), (int) (height*0.7), (int) (height*0.7));
        playerPanel.setLayout(new GridLayout(10, 10, 5, 5));
        playerColorPanel.add(playerPanel);

        playerCell = new ShipPanel[10][10];
        int playid = 0;
        for (int i = 0; i < playerCell.length; i++) {
            for (int j = 0; j < playerCell[i].length; j++) {
                playerCell[i][j] = new ShipPanel(playid++);
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

        JPanel enemyColorPanel = new JPanel();
        enemyColorPanel.setBackground(Color.RED);
        enemyColorPanel.setLayout(null);
        basePanel.add(enemyColorPanel);

        JPanel enemyPanel = new JPanel();
        enemyPanel.setBackground(Color.blue);
        enemyPanel.setBounds((int) (height*0.006), (int) (height*0.025), (int) (height*0.7), (int) (height*0.7));
        enemyPanel.setLayout(new GridLayout(10, 10, 5, 5));
        enemyColorPanel.add(enemyPanel);

        enemyCell = new ShipPanel[10][10];
        int enemyid = 0;
        for (int i = 0; i < enemyCell.length; i++) {
            for (int j = 0; j < enemyCell[i].length; j++) {
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

    public void updateEnemyPanel() {
        for (int i = 0; i < playerCell.length; i++) {
            for (int j = 0; j < playerCell[0].length; j++) {
                if (playerCell[i][j].getStatus() == ShipPanel.Status.FREE) {
                    playerCell[i][j].setBackground(Color.RED);
                    playerCell[i][j].repaint();
                } else if (playerCell[i][j].getStatus() == ShipPanel.Status.SUNKEN) {
                    playerCell[i][j].setBackground(Color.GRAY);
                    playerCell[i][j].repaint();
                }
            }
        }
    }



    public static void main(String[] args) {
        JFrame testWindow = new JFrame();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) ((int) size.getWidth() - size.getWidth() / 5);
        int height = (int) ((int) size.getHeight() - size.getHeight() / 5);
        Container cp = testWindow.getContentPane();
        cp.setLayout(null);

        testWindow.setTitle("TestFenster");
        testWindow.setSize(width, height);
        testWindow.setResizable(false);
        testWindow.setLocationRelativeTo(null);
        testWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        PlayPanel playPanel = new PlayPanel(width,height,new GUIControl(new Control()));
        playPanel.setBounds(0,0,width,height);
        cp.add(playPanel);

        testWindow.setVisible(true);
    }

}
