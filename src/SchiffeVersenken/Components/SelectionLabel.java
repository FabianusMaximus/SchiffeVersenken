package SchiffeVersenken.Components;

import SchiffeVersenken.Ship;

import javax.swing.*;

public class SelectionLabel extends JLabel {
    private Ship linkedShip;

    public SelectionLabel(String text, Ship ship){
        super(text);
        linkedShip = ship;

    }

    public Ship getLinkedShip() {
        return linkedShip;
    }

    public void setLinkedShip(Ship linkedShip) {
        this.linkedShip = linkedShip;
    }
}
