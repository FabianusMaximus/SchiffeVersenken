package SchiffeVersenken.Components;

import SchiffeVersenken.GameObjects.Ship;

import javax.swing.*;

public class ShipPanel extends JPanel {
    private int id;
    private boolean belegt;
    private Ship linkedShip;

    public ShipPanel() {
        belegt = false;
    }

    public ShipPanel(Ship ship) {
        linkedShip = ship;
        belegt = true;
    }

    public int getId() {
        return id;
    }

    public boolean isBelegt() {
        return belegt;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setBelegt(boolean belegt) {
        this.belegt = belegt;
    }


    public Ship getLinkedShips() {
        return linkedShip;
    }

    public void setLinkedShip(Ship linkedShip) {
        this.linkedShip = linkedShip;
    }
}
