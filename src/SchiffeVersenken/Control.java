package SchiffeVersenken;


import SchiffeVersenken.Fenster.GUI;
import SchiffeVersenken.Fenster.GUIControl;
import SchiffeVersenken.GameObjects.Ship;

public class Control {
    private GUIControl guiControl;
    private Ship[] ships;

    public Control() {
        generateShips();
    }

    private void openGUI() {
        if (guiControl == null) {
            guiControl = new GUIControl(this);
        } else {
            System.out.println("GUI already exists");
        }
    }

    private void generateShips() {
        ships = new Ship[]
                {new Ship("Schlachtschiff", 5),
                        new Ship("Kreuzer", 4), new Ship("Zerstörer", 3),
                        new Ship("U-Boot", 2), new Ship("U-Boot", 2)};
    }


    public boolean isCellValid(int x, int y) {

        return false;
    }

    public void setCellSelected(int x, int y) {

    }

    public Ship getShip(int index) {
        return ships[index];
    }

    public void start() {
        openGUI();
    }
}
