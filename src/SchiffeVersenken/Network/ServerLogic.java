package SchiffeVersenken.Network;

import SchiffeVersenken.Components.ShipPanel;

public class ServerLogic {
    private ClientHandler clientHandler1, clientHandler2;
    private boolean ready1, ready2;
    private ShipPanel[][] gameField1, gameField2;
    private Server server;

    public ServerLogic(ClientHandler clientHandler1, ClientHandler clientHandler2, Server server) {

        this.clientHandler1 = clientHandler1;
        this.clientHandler2 = clientHandler2;
        this.server = server;

        gameField1 = new ShipPanel[10][10];
        gameField2 = new ShipPanel[10][10];

        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameField1[i][j] = new ShipPanel(count);
                gameField2[i][j] = new ShipPanel(count);
            }
        }

    }

    public void clientReady(ClientHandler clientHandler) {
        if (clientHandler == clientHandler1) {
            ready1 = true;
            server.writeInConsole("Client 1 ready");
        } else if (clientHandler == clientHandler2) {
            ready2 = true;
            server.writeInConsole("Client 2 ready");
        } else {
            throw new RuntimeException();
        }
        if (ready1 && ready2) {
            //TODO mach was cooles
        }
    }

    /**
     * Baut aus dem String der Über das Netz geschickt wurde wieder ein normales Gamefield
     *
     * @param input Input-String des Sockets
     */
    public ShipPanel[][] translateGamefield(String input) {

        char[] field = new char[input.length() - 5];
        input.getChars(5, input.length(), field, 0);

        ShipPanel[][] holdField = new ShipPanel[10][10];
        int id = 0;
        for (int i = 0; i < holdField.length; i++) {
            for (int j = 0; j < holdField[0].length; j++) {
                holdField[i][j] = new ShipPanel(id);
                id++;
            }
        }

        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                holdField[i][j].setBlocked(field[count] == '1');
                count++;
            }
        }
        return holdField;
    }

    public void setGameField(ClientHandler clientHandler, String string) {
        if (clientHandler == clientHandler1) {
            gameField1 = translateGamefield(string);
        } else if (clientHandler == clientHandler2) {
            gameField2 = translateGamefield(string);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Funktion zum Überprüfen, ob das gamefield auch richtig übersetzt wurde
     */
    public void printGamefields() {
        System.out.println("Gamefield form Client 1:");
        for (ShipPanel[] shipPanels : gameField1) {
            for (int j = 0; j < gameField1[0].length; j++) {
                System.out.print(shipPanels[j].isBlocked() + "\t");
            }
            System.out.println();
        }

        System.out.println("Gamefield form Client 2:");
        for (ShipPanel[] shipPanels : gameField2) {
            for (int j = 0; j < gameField2[0].length; j++) {
                System.out.print(shipPanels[j].isBlocked() + "\t");
            }
            System.out.println();
        }
    }
}
