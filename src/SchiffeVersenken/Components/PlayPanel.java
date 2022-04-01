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
                enemyCell[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println(e.getButton());
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            //TODO Muss Signal schicken und von Server überprüft werden
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
        for (ShipPanel[] shipPanels : playerCell) {
            for (int j = 0; j < playerCell[0].length; j++) {
                if (shipPanels[j].getStatus() == ShipPanel.Status.LOADED) {
                    shipPanels[j].setBackground(Color.GREEN);
                    shipPanels[j].repaint();
                } else if (shipPanels[j].getStatus() == ShipPanel.Status.SUNKEN) {
                    shipPanels[j].setBackground(Color.GRAY);
                    shipPanels[j].repaint();
                }
            }
        }
    }

    /**
     * Durchläuft das gesamte Gegner Spielfeld und färbt die Felder in die entsprechenden Farben ein
     */
    public void updateEnemyPanel() {
        for (ShipPanel[] shipPanels : enemyCell) {
            for (int j = 0; j < playerCell[0].length; j++) {
                if (shipPanels[j].getStatus() == ShipPanel.Status.MISSED) {
                    shipPanels[j].setBackground(Color.RED);
                    shipPanels[j].repaint();
                } else if (shipPanels[j].getStatus() == ShipPanel.Status.SUNKEN) {
                    shipPanels[j].setBackground(Color.GRAY);
                    shipPanels[j].repaint();
                }
            }
        }
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
     * @param shipCell übergabe des zuvor ausgewählten Felds auf dem Gegner Spielfeld
     */
    public void changeEnemyCellStatus(ShipPanel shipCell) {
        for (ShipPanel[] shipPanels : enemyCell) {
            for (int j = 0; j < shipPanels.length; j++) {
                if (shipPanels[j].getId() == shipCell.getId()) {
                    shipPanels[j].setStatus(shipCell.getStatus());
                }
            }
        }
        updateEnemyPanel();
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

        PlayPanel playPanel = new PlayPanel(width, height, new GUIControl(new Control()));
        playPanel.setBounds(0, 0, width, height);
        cp.add(playPanel);

        ShipPanel schiffchen = new ShipPanel(5);
        schiffchen.setStatus(ShipPanel.Status.SUNKEN);

        ShipPanel keinSchiffchen = new ShipPanel(9);
        keinSchiffchen.setStatus(ShipPanel.Status.MISSED);

        playPanel.changePlayerTurn(false);
        playPanel.changeEnemyCellStatus(schiffchen);
        playPanel.changeEnemyCellStatus(keinSchiffchen);

        testWindow.setVisible(true);
    }

}
