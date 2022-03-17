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

        int count = 0;
        for (int i = 0; i <10 ; i++) {
            for (int j = 0; j <10 ; j++) {
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
        if (ready1 && ready2){
            printGamefields();
            //TODO mach was cooles
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
