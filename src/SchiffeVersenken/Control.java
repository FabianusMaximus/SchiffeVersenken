package SchiffeVersenken;


import SchiffeVersenken.GameObjects.GameField;
import SchiffeVersenken.GameObjects.Player;
import SchiffeVersenken.GameObjects.Ship;

public class Control {
    GUI theGUI;
    GameField theGameField;
    Player player1;
    Player player2;

    public Control() {
        theGameField = new GameField();
    }

    private void openGUI() {
        if (theGUI == null) {
            theGUI = new GUI(this);
        } else {
            System.out.println("GUI allready exists");
        }
    }

    private void generateShips() {
        player1.addShips(new Ship("Schlachtschiff", 5));
        player2.addShips(new Ship("Schlachtschiff", 5));

        for (int i = 0; i <2 ; i++) {
            player1.addShips(new Ship("Kreuzer", 4));
            player2.addShips(new Ship("Kreuzer", 4));
        }

        for (int i = 0; i <3 ; i++) {
            player1.addShips(new Ship("Zerstörer", 3));
            player2.addShips(new Ship("Zerstörer", 3));
        }

        for (int i = 0; i <4 ; i++) {
            player1.addShips(new Ship("U-Boot", 2));
            player2.addShips(new Ship("U-Boot", 2));
        }


    }

    private void printField() {
        theGameField.printField();
    }

    public boolean isCellValid(int x, int y) {
        boolean valid = false;

        return valid;
    }

    public void setCellSelected(int x, int y){

    }

    public void start() {
        openGUI();
        printField();

    }
}
