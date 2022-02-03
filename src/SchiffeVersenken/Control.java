package SchiffeVersenken;


import SchiffeVersenken.Fenster.GUI;
import SchiffeVersenken.GameObjects.Player;
import SchiffeVersenken.GameObjects.Ship;

import java.util.ArrayList;

public class Control {
    private GUI theGUI;
    private ArrayList<Ship> ships = new ArrayList<>();

    public Control() {
        generateShips();
    }

    private void openGUI() {
        if (theGUI == null) {
            theGUI = new GUI(this);
        } else {
            System.out.println("GUI allready exists");
        }
    }

    private void generateShips() {
        ships.add(new Ship("Schlachtschiff", 5));

        for (int i = 0; i < 2; i++) {
           ships.add(new Ship("Kreuzer", 4));
        }

        for (int i = 0; i < 3; i++) {
            ships.add(new Ship("Zerstörer", 3));
        }

        for (int i = 0; i < 4; i++) {
            ships.add(new Ship("U-Boot", 2));
        }
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public boolean isCellValid(int x, int y) {
        boolean valid = false;

        return valid;
    }

    public void setCellSelected(int x, int y) {

    }

    public void start() {
        openGUI();
    }
}
