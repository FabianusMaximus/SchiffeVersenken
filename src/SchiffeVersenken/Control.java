package SchiffeVersenken;


import SchiffeVersenken.GameObjects.GameField;
import SchiffeVersenken.GameObjects.Player;

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
        if (player1 == null) {
            player1 = new Player();
        }
        if (player2 == null) {
            player2 = new Player();
        }
    }

    private void generateShips() {

    }

    private void printField() {
        theGameField.printField();
    }

    public boolean isCellValid(int x, int y) {
        boolean valid = false;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {

            }
        }
        return valid;
    }

    public void setCellSelected(int x, int y){

    }

    public void start() {
        openGUI();
        printField();

    }
}
