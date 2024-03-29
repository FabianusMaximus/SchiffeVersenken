package SchiffeVersenken.Network;

import SchiffeVersenken.Components.ShipPanel;

import java.io.IOException;
import java.util.ArrayList;

public class ServerLogic {
    private ClientHandler clientHandler1, clientHandler2;
    private boolean ready1, ready2;
    private Server server;
    private boolean activePlayer;

    private int iD;

    private ShipPanel[][] gameField1, gameField2;


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
            clientHandler1.goToPlayScreen(false);
            clientHandler2.goToPlayScreen(true);
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
                holdField[i][j].setLoaded(field[count] == '1');
                count++;
            }
        }
        return holdField;
    }

    public void setGameField(ClientHandler clientHandler, String string) {
        if (clientHandler == clientHandler1) {
            gameField1 = translateGamefield(string);
            System.out.println("GameField from Client 1: ");
            printGamefield(gameField1);
        } else if (clientHandler == clientHandler2) {
            gameField2 = translateGamefield(string);
            System.out.println("GameField from Client 2:");
            printGamefield(gameField2);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Funktion, die das Spielfeld in der Console anzeigt, um zu schauen, ob es richtig übersetzt wurde
     */
    public void printGamefield(ShipPanel[][] gameField) {
        for (ShipPanel[] shipPanels : gameField) {
            for (int j = 0; j < gameField[0].length; j++) {
                System.out.print(shipPanels[j].isLoaded() + "\t");
            }
            System.out.println();
        }

    }

    public void verarbeitenShot(ClientHandler clientHandler, String string) {
        String hold = string.split(":")[1];
        iD = Integer.parseInt(hold);

        if (clientHandler == clientHandler1) {
            if (vergleichenID(iD, gameField2)) {
                sendTreffer(clientHandler1, clientHandler2);
            } else {
                sendMissed(clientHandler1, clientHandler2);
            }
        } else {
            if (vergleichenID(iD, gameField1)) {
                sendTreffer(clientHandler2, clientHandler1);
            } else {
                sendMissed(clientHandler2, clientHandler1);
            }
        }
    }

    public boolean vergleichenID(int iD, ShipPanel[][] gameField) {
        int holdID = 0;
        for (ShipPanel[] fields : gameField) {
            for (ShipPanel field : fields) {
                holdID = field.getId();
                if (holdID == iD) {
                    return field.getStatus() == ShipPanel.Status.LOADED;
                }
            }
        }
        return false;
    }

    public void sendTreffer(ClientHandler schiessender, ClientHandler empfangender) {
        try {
            schiessender.sendMessage("treffer");
            empfangender.sendMessage("schuss:" + iD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMissed(ClientHandler schiessender, ClientHandler empfangender) {
        try {
            schiessender.sendMessage("missed");
            empfangender.sendMessage("schuss:" + iD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void weiterleitenMessage(ClientHandler clienthandler, String message) {
        try {
            if (clienthandler == clientHandler1) clientHandler2.sendMessage(message);
            else clientHandler1.sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
