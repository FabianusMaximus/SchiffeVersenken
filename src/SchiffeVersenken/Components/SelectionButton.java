package SchiffeVersenken.Components;

import SchiffeVersenken.Ship;

import javax.swing.*;
import java.util.ArrayList;

public class SelectionButton extends JButton {
    private ArrayList<Ship> linkedShips;

    public SelectionButton(String text) {
        super(text);
    }

    public void addShips(Ship ship) {
        this.linkedShips.add(ship);
    }

    public void removeShip(){
        this.linkedShips.remove(0);
    }

    public Ship getLinkedShip(int index) {
        return this.linkedShips.get(index);
    }

    public ArrayList<Ship> setLinkedShips(ArrayList<Ship> ships) {
        return this.linkedShips = ships;
    }
}
