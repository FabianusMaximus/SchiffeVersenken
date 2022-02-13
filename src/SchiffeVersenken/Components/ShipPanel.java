package SchiffeVersenken.Components;

import SchiffeVersenken.GameObjects.Ship;

import javax.swing.*;

public class ShipPanel extends JPanel {
    private int id;
    private Ship linkedShip;
    private Status status;

    public ShipPanel(int id) {
        this.id = id;
        status = Status.FREE;
        linkedShip = null;
    }

    public ShipPanel(Ship ship) {
        linkedShip = ship;
        status = Status.LOADED;
    }

    public int getId() {
        return id;
    }

    public boolean isBelegt() {
        return status != Status.FREE;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLinkedShip(Ship ship) {
        linkedShip = ship;
    }

    public Ship getLinkedShip() {
        return linkedShip;
    }


    public void setBelegt(boolean belegt) {
        if (belegt) {
            status = Status.LOADED;
        } else {
            status = Status.FREE;
        }

    }


    public Ship getLinkedShips() {
        return linkedShip;
    }

    public boolean isBlocked() {
        return status == Status.BLOCKED;
    }

    public void setBlocked(boolean blocked) {
        if (blocked) {
            status = Status.BLOCKED;
        } else {
            status = Status.FREE;
        }

    }

    enum Status {
        FREE, LOADED, BLOCKED;
    }
}
