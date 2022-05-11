package SchiffeVersenken.Components;

import SchiffeVersenken.Control;
import SchiffeVersenken.Fenster.GUIControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayPanel extends CustomPanel {

    private ShipPanel[][] playerCell;
    private ShipPanel[][] enemyCell;
    private JLabel playerTurn;

    public PlayPanel(int width, int height, GUIControl guiControl) {
        super(width, height, guiControl);
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        Font standardFont = new Font("Times new Roman", Font.PLAIN, width / 20);

        JPanel basePanel = new JPanel();
        basePanel.setBounds((int) (width * 0.07), (int) ((width / 2) * 0.04), (int) (width * 0.85), (int) ((width / 2) * 0.85));
        basePanel.setLayout(new GridLayout(1, 2, this.width / 20, 0));
        basePanel.setBackground(Color.WHITE);
        this.add(basePanel);

        JPanel playerColorPanel = new JPanel();
        playerColorPanel.setBackground(Color.GREEN);
        playerColorPanel.setLayout(null);
        basePanel.add(playerColorPanel);

        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(Color.blue);
        playerPanel.setBounds((int) (height * 0.006), (int) (height * 0.025), (int) (height * 0.7), (int) (height * 0.7));
        playerPanel.setLayout(new GridLayout(10, 10, 5, 5));
        playerColorPanel.add(playerPanel);

        playerCell = new ShipPanel[10][10];
        int playid = 0;
        for (int i = 0; i < playerCell.length; i++) {
            for (int j = 0; j < playerCell[i].length; j++) {
                playerCell[i][j] = new ShipPanel(playid++);
                playerCell[i][j].setBackground(Color.black);
                playerPanel.add(playerCell[i][j]);
            }
        }

        JPanel enemyColorPanel = new JPanel();
        enemyColorPanel.setBackground(Color.RED);
        enemyColorPanel.setLayout(null);
        basePanel.add(enemyColorPanel);

        JPanel enemyPanel = new JPanel();
        enemyPanel.setBackground(Color.blue);
        enemyPanel.setBounds((int) (height * 0.006), (int) (height * 0.025), (int) (height * 0.7), (int) (height * 0.7));
        enemyPanel.setLayout(new GridLayout(10, 10, 5, 5));
        enemyColorPanel.add(enemyPanel);

        enemyCell = new ShipPanel[10][10];
        int enemyid = 0;
        for (int i = 0; i < enemyCell.length; i++) {
            for (int j = 0; j < enemyCell[i].length; j++) {
                enemyCell[i][j] = new ShipPanel(enemyid++);
                enemyCell[i][j].setBackground(Color.black);
                int finalI = i;
                int finalJ = j;
                enemyCell[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1 &&
                                enemyCell[finalI][finalJ].getStatus() == ShipPanel.Status.FREE) {
                            guiControl.shotRoutine(enemyCell[finalI][finalJ].getId());
                            System.out.println(enemyCell[finalI][finalJ].getId());
                        }
                    }
                });
                enemyPanel.add(enemyCell[i][j]);
            }
        }

        JPanel textBox = new JPanel();
        textBox.setBounds((int) (width * 0.3), (int) (height * 0.8), (int) (width * 0.4), (int) (height * 0.125));
        textBox.setBackground(Color.WHITE);
        add(textBox);

        playerTurn = new JLabel("Platzhalter");
        playerTurn.setFont(standardFont);
        textBox.add(playerTurn);

    }

    /**
     * übernimmt Spieler Feld und setzt alle Schifffelder mit entsprechendem Status
     *
     * @param shipPanels Spielfeld das Übergeben werden muss
     */
    public void updatePlayerShipStatus(ShipPanel[][] shipPanels) {
        for (int i = 0; i < playerCell.length; i++) {
            for (int j = 0; j < playerCell[i].length; j++) {
                playerCell[i][j].setStatus(shipPanels[i][j].getStatus());
            }
        }
        updatePlayerPanel();
    }

    /**
     * Ändert Status des eigenes Schiffes
     *
     * @param schiffFeld schiffeld dessen Status geändert werden muss
     */
    public void changePlayerShipStatus(ShipPanel schiffFeld) {
        for (ShipPanel[] shipPanels : playerCell) {
            for (int j = 0; j < shipPanels.length; j++) {
                if (shipPanels[j].getId() == schiffFeld.getId()) {
                    shipPanels[j].setStatus(schiffFeld.getStatus());
                }
            }
        }
        updatePlayerPanel();
    }

    /**
     * Gibt Spielerfeld zurück
     *
     * @return
     */
    public ShipPanel[][] getPlayerCell() {
        return playerCell;
    }

    /**
     * färbt das Spielerfeld neu ein
     */
    public void updatePlayerPanel() {
        applyColorScheme(playerCell);
    }

    public void updateStatusPlayerPanel(int iD, ShipPanel.Status status) {
        for (ShipPanel[] shipPanels : playerCell) {
            for (ShipPanel shipPanel : shipPanels) {
                if (shipPanel.getId() == iD) {
                    shipPanel.setStatus(status);
                }
            }
        }
        updatePlayerPanel();
    }

    /**
     * Durchläuft das gesamte Gegner Spielfeld und färbt die Felder in die entsprechenden Farben ein
     */
    public void updateEnemyPanel() {
        applyColorScheme(enemyCell);
    }

    private void applyColorScheme(ShipPanel[][] field) {
        for (ShipPanel[] shipPanels : field) {
            for (ShipPanel shipPanel : shipPanels) {
                switch (shipPanel.getStatus()) {
                    case LOADED -> shipPanel.setBackground(Color.GREEN);
                    case MISSED -> shipPanel.setBackground(Color.RED);
                    case SUNKEN -> shipPanel.setBackground(Color.GRAY);
                    case HIT -> shipPanel.setBackground(Color.ORANGE);
                }
            }
        }
        this.repaint();
    }

    /**
     * Setzt bei playerTurn die Ausgabe, ob ein Spieler dran ist oder warten muss.
     *
     * @param pBoolean true Spieler ist dran und färbt Grün/ false Spieler muss warten und färbt rot
     */
    public void changePlayerTurn(Boolean pBoolean) {
        if (pBoolean) {
            playerTurn.setText("Du bist am Zug");
            playerTurn.setForeground(Color.green);
        } else {
            playerTurn.setText("Warten...");
            playerTurn.setForeground(Color.RED);
        }
        playerTurn.repaint();
    }

    /**
     * ändert den Status eines Feldes auf dem Gegner spielfeld
     * und benutzt gleich updateEnemyPanel();
     *
     * @param iD     ID auf der Celle die geupdatet werden soll
     * @param status Status den die Zelle erhalten soll
     */
    public void changeEnemyCellStatus(int iD, ShipPanel.Status status) {
        for (ShipPanel[] shipPanels : enemyCell) {
            for (ShipPanel shipPanel : shipPanels) {
                if (shipPanel.getId() == iD) {
                    shipPanel.setStatus(status);
                }
            }
        }
        updateEnemyPanel();
    }

    /**
     * Gibt die Celle des Spielers zurück, das die ID hat
     *
     * @param iD ID der Zelle
     * @return Celle des Spielers
     */
    public ShipPanel getPlayerCell(int iD) {
        for (ShipPanel[] shipPanels : playerCell) {
            for (ShipPanel shipPanel : shipPanels) {
                if (iD == shipPanel.getId()) {
                    return shipPanel;
                }
            }
        }
        return null;
    }

}
