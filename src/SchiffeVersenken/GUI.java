package SchiffeVersenken;

import SchiffeVersenken.Components.SelectionLabel;
import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.GameObjects.Ship;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
    private Control control;
    private int width;
    private int height;
    private JPanel field;
    private JPanel selectionPanel;
    private SelectionLabel[] selectShips;
    private ShipPanel[][] cell;
    private Container cp;
    private JLabel label;

    private Point pos = new Point();
    private int move;
    private Ship selectedShip;

    private Font standardFont;

    public GUI(Control control) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) ((int) size.getWidth() - size.getWidth() / 5);
        height = (int) ((int) size.getHeight() - size.getHeight() / 5);
        this.control = control;
        cp = this.getContentPane();
        cp.setLayout(null);

        standardFont = new Font("Times new Roman", Font.PLAIN, width / 50);

        setTitle("Schiffeversenken - GUI");
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        field = new JPanel();
        field.setBackground(Color.blue);
        field.setSize((int) (height / 1.5), (int) (height / 1.5));
        field.setLocation(width / 4 - field.getWidth() / 2, height / 2 - field.getHeight() / 2);
        field.setLayout(new GridLayout(10, 10, 5, 5));
        cp.add(field);

        selectionPanel = new JPanel();
        selectionPanel.setSize((int) (height / 1.5), (int) (height / 1.5));
        selectionPanel.setLocation(width / 3 + selectionPanel.getWidth() / 2, height / 2 - selectionPanel.getHeight() / 2);
        selectionPanel.setLayout(new GridLayout(2, 2, 10, 10));
        cp.add(selectionPanel);

        selectShips = new SelectionLabel[5];
        String[] names = {"Schlachtschiff", "Kreuzer", "Zerstörer", "U-Bot", "U-Bot"};
        for (int i = 0; i < selectShips.length; i++) {
            selectShips[i] = new SelectionLabel(names[i], control.getShip(i));
            selectShips[i].setBorder(new LineBorder(Color.black));
            selectShips[i].setHorizontalAlignment(SwingConstants.CENTER);
            selectShips[i].setVerticalAlignment(SwingConstants.CENTER);
            selectShips[i].setFont(standardFont);
            int finalI = i;
            selectShips[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectShip(selectShips[finalI].getLinkedShip());
                    selectionPanel.remove(selectShips[finalI]);
                }
            });
            selectShips[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    selectShips[finalI].setBackground(Color.lightGray);
                }
            });
            selectShips[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    selectShips[finalI].setBackground(Color.white);
                }
            });
            selectionPanel.add(selectShips[i]);
        }

        cell = new ShipPanel[10][10];
        int id = 0;
        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[i].length; j++) {
                cell[i][j] = new ShipPanel(id++);
                cell[i][j].setBackground(Color.black);
                int finalX = i;
                int finalY = j;
                cell[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        clickCell(finalX, finalY, selectedShip);
                    }
                });
                field.add(cell[i][j]);
            }
        }


        setVisible(true);
    }

    /**
     * schaut wo der nächste Move hingeht
     *
     * @param x
     * @param y
     * @return 0 = Waagerecht, 1 = Senkrecht, 2 = Fehler
     */
    private int getNextMove(int x, int y) {
        if (move != 0) {
            if (pos.getY() == y + 1 || pos.getY() == y - 1) {
                return 0;
            } else if (x == pos.getX() + 1 || x == pos.getX() - 1) {
                return 1;
            }
        }
        return 2;

    }

    /**
     * Funktion die beim Click ausgeführt wird
     *
     * @param x    Erste Koordinate des Felds
     * @param y    Zweite Koordinate des Fels
     * @param ship Objekt von Schiff, das plaziert werden soll
     */
    private void clickCell(int x, int y, Ship ship) {
        System.out.println("PanelID: " + cell[x][y].getId());
        if (!cell[x][y].isBelegt() && selectedShip != null) {
            placeShip(x, y, ship);
        } else if (cell[x][y].isBelegt() && selectedShip == null) {
            Ship holdShip = cell[x][y].getLinkedShip();
            removeShip(x, y, holdShip);
            holdShip.changeOrientation();
            placeShip(x, y, holdShip);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Du hast kein Schiff ausgewählt",
                    "Placement Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        updateGamefield();

    }

    /**
     * Funktion die das Schiff entsprechend seiner Größe und seiner Orientierung auf dem Spielfeld platziert
     *
     * @param x    Erste Koordinate, an dem das Schiff platziert werden, soll
     * @param y    Zweite Koordinate, an dem das Schiff platziert werden, soll
     * @param ship Schiff das platziert werden soll
     */
    public void placeShip(int x, int y, Ship ship) {
        for (int i = 0; i < ship.applyOrientation(x, y).size(); i++) {
            int a = (int) ship.applyOrientation(x, y).get(i).getX();
            int b = (int) ship.applyOrientation(x, y).get(i).getY();
            cell[a][b].setBelegt(true);
            cell[a][b].setLinkedShip(ship);
            selectedShip = null;
        }
        for (int i = 0; i < ship.generateBlockedZone(x, y).size(); i++) {
            int a = (int) ship.generateBlockedZone(x, y).get(i).getX();
            int b = (int) ship.generateBlockedZone(x, y).get(i).getY();
            cell[a][b].setBlocked(true);
        }
    }

    private void removeShip(int x, int y, Ship ship) {
        for (int i = 0; i < ship.applyOrientation(x, y).size(); i++) {
            int a = (int) ship.applyOrientation(x, y).get(i).getX();
            int b = (int) ship.applyOrientation(x, y).get(i).getY();
            cell[a][b].setBelegt(false);
            cell[a][b].setLinkedShip(null);
            selectedShip = null;
        }
    }

    public void updateGamefield() {
        for (ShipPanel[] shipPanels : cell) {
            for (ShipPanel shipPanel : shipPanels) {
                if (!shipPanel.isBelegt() && !shipPanel.isBlocked()) {
                    shipPanel.setBackground(Color.black);
                } else if (shipPanel.isBlocked()) {
                    shipPanel.setBackground(Color.gray);
                } else {
                    shipPanel.setBackground(Color.green);
                }
            }
        }
        this.revalidate();
    }

    public void selectShip(Ship ship) {
        selectedShip = ship;
        System.out.println("Selectet Ship: " + selectedShip.getName());
    }


    public static void main(String[] args) {
        new GUI(new Control());
    }
}
