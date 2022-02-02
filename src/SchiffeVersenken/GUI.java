package SchiffeVersenken;

import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.GameObjects.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
    private Control control;
    private int width;
    private int height;
    private JPanel field;
    private JPanel selectionPanel;
    private ShipPanel[][] cell;
    private Container cp;
    private JLabel label;

    private Point pos = new Point();
    private int move;
    private Ship selectedShip = new Ship("penis", 3);

    public GUI(Control control) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) ((int) size.getWidth() - size.getWidth() / 5);
        height = (int) ((int) size.getHeight() - size.getHeight() / 5);
        this.control = control;
        cp = this.getContentPane();
        cp.setLayout(null);

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
        selectionPanel.setBackground(Color.green);
        selectionPanel.setSize((int) (height / 1.5), (int) (height / 1.5));
        selectionPanel.setLocation(width / 3 + selectionPanel.getWidth()/2 , height / 2 - selectionPanel.getHeight()/2);
        selectionPanel.setLayout(null);
        cp.add(selectionPanel);

        cell = new ShipPanel[10][10];

        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[i].length; j++) {
                cell[i][j] = new ShipPanel();
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
        if (selectedShip == null) {
            JOptionPane.showMessageDialog(this,
                    "Du hast kein Schiff ausgewählt",
                    "Placement Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                if (!cell[x][y].isBelegt()) {
                    cell[x][y].setBackground(Color.green);
                    placeShip(x, y, ship);
                } else {
                    ship.changeOrientation();
                    for (int i = 0; i < cell.length; i++) {
                        for (int j = 0; j < cell[i].length; j++) {
                            if (cell[i][j].isBelegt()) {
                                cell[i][j].setBackground(Color.black);
                                cell[i][j].setBelegt(false);
                            }
                        }
                    }
                    placeShip(x, y, ship);
                }
            }catch (IndexOutOfBoundsException e){
                cell[x][y].setBackground(Color.red);
            }

        }

    }

    public void placeShip(int x, int y, Ship ship) {
        for (int i = 0; i < ship.applyOrientation(x, y).size(); i++) {
            int a = (int) ship.applyOrientation(x, y).get(i).getX();
            int b = (int) ship.applyOrientation(x, y).get(i).getY();
            cell[a][b].setBackground(Color.green);
            cell[a][b].setBelegt(true);
        }
    }


    public static void main(String[] args) {
        new GUI(new Control());
    }
}
