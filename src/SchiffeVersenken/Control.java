package SchiffeVersenken;

import SchiffeVersenken.Schiffe.Kreuzer;
import SchiffeVersenken.Schiffe.Schlachtschiff;
import SchiffeVersenken.Schiffe.UBoot;
import SchiffeVersenken.Schiffe.Zerstörer;

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
        player1.addShips(new Schlachtschiff());
        player2.addShips(new Schlachtschiff());
        for (int i = 0; i < 2; i++) {
            player1.addShips(new Kreuzer());
            player2.addShips(new Kreuzer());

        }
        for (int i = 0; i < 3; i++) {
            player1.addShips(new Zerstörer());
            player2.addShips(new Zerstörer());
        }
        for (int i = 0; i < 4; i++) {
            player1.addShips(new UBoot());
            player2.addShips(new UBoot());
        }

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
