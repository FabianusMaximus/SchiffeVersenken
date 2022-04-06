package SchiffeVersenken.Components;

import SchiffeVersenken.Ship;

import javax.swing.*;
import java.awt.*;

public class ShipPanel extends JPanel {
    private int id;
    private Ship linkedShip;
    private Status status;

    public ShipPanel(int id) {
        super();
        this.id = id;
        status = Status.FREE;
        linkedShip = null;
    }

    public ShipPanel(int id, Ship ship) {
        super();
        linkedShip = ship;
        status = Status.LOADED;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isBelegt() {
        return status != Status.FREE;
    }

    public boolean isShip(){
        return status == Status.LOADED;
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

    public void setError(boolean error) {
        if (error) {
            status = Status.ERROR;
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

    public boolean isError(){
        return status == Status.ERROR;
    }

    public void setBlocked(boolean blocked) {
        if (blocked) {
            status = Status.BLOCKED;
        } else {
            status = Status.FREE;
        }

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        FREE, LOADED, BLOCKED, ERROR, SUNKEN, MISSED
    }
}
