package SchiffeVersenken.Fenster;

import SchiffeVersenken.Components.SelectionButton;
import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.Control;
import SchiffeVersenken.GameObjects.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
    private Control control;
    private int width;
    private int height;
    private JPanel field;
    private JPanel selectionPanel;
    private SelectionButton[] selectionButtons;
    private ShipPanel[][] cell;
    private Container cp;
    private JLabel label;

    private Point pos = new Point();
    private int move;
    private Ship selectedShip;

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
        {
            field = new JPanel();
            field.setBackground(Color.blue);
            field.setSize((int) (height / 1.5), (int) (height / 1.5));
            field.setLocation(width / 4 - field.getWidth() / 2, height / 2 - field.getHeight() / 2);
            field.setLayout(new GridLayout(10, 10, 5, 5));
            cp.add(field);

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
        }//gameField

        {
            selectionPanel = new JPanel();
            selectionPanel.setSize((int) (height / 1.5), (int) (height / 1.5));
            selectionPanel.setLocation(width / 3 + selectionPanel.getWidth() / 2,
                    height / 2 - selectionPanel.getHeight() / 2);
            selectionPanel.setLayout(new GridLayout(4, 1, 10, 10));
            cp.add(selectionPanel);

            selectionButtons = new SelectionButton[4];
            selectionButtons[0] = new SelectionButton("U-Boot");
            selectionButtons[0].setLinkedShips(control.getShips());

            selectionButtons[1] = new SelectionButton("Zerstörer");
            selectionButtons[1].setLinkedShips(control.getShips());

            selectionButtons[2] = new SelectionButton("Kreuzer");
            selectionButtons[2].setLinkedShips(control.getShips());

            selectionButtons[3] = new SelectionButton("Schlachtschiff");
            selectionButtons[3].setLinkedShips(control.getShips());

            for (int i = 0; i < selectionButtons.length; i++) {
                selectionButtons[i].setBackground(Color.black);
                selectionButtons[i].setForeground(Color.white);
                int finalI = i;
                selectionButtons[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        selectShip(finalI);
                    }
                });
                selectionPanel.add(selectionButtons[i]);
            }
        }//SelectionField


        setVisible(true);
    }

    private void selectShip(int index) {
        if (selectionButtons[index].getLinkedShip(0) == null) {
            JOptionPane.showMessageDialog(this,
                    "Du hast keine Schiffe mehr von der Sorte",
                    "Selection Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            selectedShip = selectionButtons[index].getLinkedShip(0);
            selectionButtons[index].removeShip();
        }


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
                    for (ShipPanel[] shipPanels : cell) {
                        for (ShipPanel shipPanel : shipPanels) {
                            if (shipPanel.isBelegt()) {
                                shipPanel.setBackground(Color.black);
                                shipPanel.setBelegt(false);
                                shipPanel.setLinkedShip(null);
                            }
                        }
                    }
                    placeShip(x, y, ship);
                }
            } catch (IndexOutOfBoundsException e) {
                cell[x][y].setBackground(Color.red);
            }

        }

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
            cell[a][b].setBackground(Color.green);
            cell[a][b].setBelegt(true);
            cell[a][b].setLinkedShip(ship);

        }
    }


    public static void main(String[] args) {
        new GUI(new Control());
    }
}
