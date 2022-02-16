package SchiffeVersenken.Fenster;

import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.Control;
import SchiffeVersenken.GameObjects.Ship;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUIControl {
    private GUI gui;
    private Control control;

    private Ship selectedShip;

    public GUIControl(Control control) {
        this.control = control;
        this.gui = new GUI(this);
    }

    /**
     * Funktion die beim Click ausgeführt wird
     *
     * @param x Erste Koordinate des Felds
     * @param y Zweite Koordinate des Fels
     */
    public void clickCell(int x, int y) {
        System.out.println("PanelID: " + gui.getCell()[x][y].getId());
        if (!gui.getCell()[x][y].isBelegt() && !gui.getCell()[x][y].isBlocked() && selectedShip != null) {
            placeShip(x, y, selectedShip);
        } else if (gui.getCell()[x][y].isBelegt() && selectedShip == null) {
            Ship holdShip = gui.getCell()[x][y].getLinkedShip();
            removeShip(x, y, holdShip);
            selectedShip = holdShip;
        } else if((gui.getCell()[x][y].isBelegt()||gui.getCell()[x][y].isBlocked())&&selectedShip != null){
            JOptionPane.showMessageDialog(gui,
                    "Das Schiff kann hier nicht platziert werden",
                    "Placement Error",
                    JOptionPane.ERROR_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(gui,
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
        for (int i = 0; i < ship.generateBlockedZone(x, y).size(); i++) {
            int a = (int) ship.generateBlockedZone(x, y).get(i).getX();
            int b = (int) ship.generateBlockedZone(x, y).get(i).getY();
            if (checkValid(a, b)) {
                gui.getCell()[a][b].setBlocked(true);
            }
        }

        for (int i = 0; i < ship.applyOrientation(x, y).size(); i++) {
            int a = (int) ship.applyOrientation(x, y).get(i).getX();
            int b = (int) ship.applyOrientation(x, y).get(i).getY();
            if (checkValid(a, b)) {
                gui.getCell()[a][b].setBelegt(true);
                gui.getCell()[a][b].setLinkedShip(ship);
            }
        }
        selectedShip = null;
    }

    public void changeOrientation(int x, int y) {
        deletePreview(x, y);
        selectedShip.changeOrientation();
        showPreview(x, y);
    }

    private void removeShip(int x, int y, Ship ship) {
        for (int i = 0; i < ship.generateBlockedZone(x, y).size(); i++) {
            int a = (int) ship.generateBlockedZone(x, y).get(i).getX();
            int b = (int) ship.generateBlockedZone(x, y).get(i).getY();
            if (checkValid(a, b)) {
                gui.getCell()[a][b].setBlocked(false);
            }
        }

        for (int i = 0; i < ship.applyOrientation(x, y).size(); i++) {
            int a = (int) ship.applyOrientation(x, y).get(i).getX();
            int b = (int) ship.applyOrientation(x, y).get(i).getY();
            if (checkValid(a, b)) {
                gui.getCell()[a][b].setBelegt(false);
                gui.getCell()[a][b].setLinkedShip(null);
                selectedShip = null;
            }
        }
    }

    public void updateGamefield() {
        for (ShipPanel[] shipPanels : gui.getCell()) {
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
        gui.revalidate();
    }

    private ArrayList<Point> calculatePreview(int x, int y) {
        ArrayList<Point> point = new ArrayList<>();
        if (selectedShip != null) {
            for (int i = 0; i < selectedShip.applyOrientation(x, y).size(); i++) {
                int a = (int) selectedShip.applyOrientation(x, y).get(i).getX();
                int b = (int) selectedShip.applyOrientation(x, y).get(i).getY();
                if (checkValid(a, b)) {
                    point.add(new Point(a, b));
                }
            }
        }
        return point;
    }

    public void showPreview(int x, int y) {

        for (int i = 0; i < calculatePreview(x, y).size(); i++) {
            int a = (int) calculatePreview(x, y).get(i).getX();
            int b = (int) calculatePreview(x, y).get(i).getY();
            if (gui.getCell()[a][b].isBelegt() || gui.getCell()[a][b].isBlocked()) {
                gui.getCell()[a][b].setBackground(Color.red);
            } else {
                gui.getCell()[a][b].setBackground(Color.green);
            }

        }
    }

    public void deletePreview(int x, int y) {
        updateGamefield();
    }

    public void selectShip(Ship ship) {
        selectedShip = ship;
        System.out.println("Selectet Ship: " + selectedShip.getName());
    }

    public boolean checkValid(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    public Ship getShip(int index) {
        return control.getShip(index);
    }

}
