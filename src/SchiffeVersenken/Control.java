package SchiffeVersenken;


import SchiffeVersenken.Fenster.GUI;
import SchiffeVersenken.GameObjects.Player;
import SchiffeVersenken.GameObjects.Ship;

import java.util.ArrayList;

public class Control {
    private GUI theGUI;
    private GameField theGameField;
    private Ship[] ships;

    public Control() {
        theGameField = new GameField();
        generateShips();
    }

    private void openGUI() {
        if (theGUI == null) {
            theGUI = new GUI(this);
        } else {
            System.out.println("GUI already exists");
        }
    }

    private void generateShips() {
        ships = new Ship[]{new Ship("Schlachtschiff", 5), new Ship("Kreuzer", 4),
                new Ship("Zerstörer", 3), new Ship("U-Boot", 2)};
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public boolean isCellValid(int x, int y) {

        return false;
    }

    public void setCellSelected(int x, int y) {

    }

    public Ship getShip(int index){
        return ships[index];
    }

    public void start() {
        openGUI();
    }
}
