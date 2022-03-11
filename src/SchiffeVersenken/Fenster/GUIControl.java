package SchiffeVersenken.Fenster;

import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.Control;
import SchiffeVersenken.Network.ClientHandler;
import SchiffeVersenken.Network.Server;
import SchiffeVersenken.Ship;
import SchiffeVersenken.Network.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class GUIControl {
    private GUI gui;
    private Control control;

    private Ship selectedShip;

    private boolean bestaetigt = false;

    public GUIControl(Control control) {
        this.control = control;
        this.gui = new GUI(this);
    }

    /**
     * Funktion die beim Click ausgeführt wird
     *
     * @param x Erste Koordinate des Felds
     * @param y Zweite Koordinate des Fels
     */
    public void clickCell(int x, int y) {
        if (!bestaetigt) {
            System.out.println("PanelID: " + gui.getCell()[x][y].getId());
            if (!gui.getCell()[x][y].isBelegt() && !gui.getCell()[x][y].isBlocked() && selectedShip != null) {
                placeShip(x, y, selectedShip);
            } else if (gui.getCell()[x][y].isBelegt() && selectedShip == null) {
                Ship holdShip = gui.getCell()[x][y].getLinkedShip();
                removeShip(holdShip);
                selectedShip = holdShip;
            } else if ((gui.getCell()[x][y].isBelegt() || gui.getCell()[x][y].isBlocked()) && selectedShip != null) {
                JOptionPane.showMessageDialog(gui,
                        "Das Schiff kann hier nicht platziert werden",
                        "Placement Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(gui,
                        "Du hast kein Schiff ausgewählt",
                        "Placement Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            updateGamefield();
        }

    }

    /**
     * Funktion die das Schiff entsprechend seiner Größe und seiner Orientierung auf dem Spielfeld platziert
     *
     * @param x    Erste Koordinate, an dem das Schiff platziert werden, soll
     * @param y    Zweite Koordinate, an dem das Schiff platziert werden, soll
     * @param ship Schiff das platziert werden soll
     */
    public void placeShip(int x, int y, Ship ship) {
        ship.setBlockedZone(x, y);
        ship.setLocation(x, y);
        boolean outOfBounds = false;
        for (int i = 0; i < ship.getLocation().size(); i++) {
            for (int j = 0; j < ship.getLocation().size(); j++) {
                if (!checkValid((int) ship.getLocation().get(i).getX(), (int) ship.getLocation().get(j).getY())) {
                    outOfBounds = true;
                }
            }
        }

        for (int i = 0; i < ship.getBlockedZone().size(); i++) {
            int a = (int) ship.getBlockedZone().get(i).getX();
            int b = (int) ship.getBlockedZone().get(i).getY();
            if (checkValid(a, b) && !outOfBounds) {
                gui.getCell()[a][b].setBlocked(true);
            }
        }

        for (int i = 0; i < ship.getLocation().size(); i++) {
            int a = (int) ship.getLocation().get(i).getX();
            int b = (int) ship.getLocation().get(i).getY();
            if (checkValid(a, b) && !outOfBounds) {
                gui.getCell()[a][b].setBelegt(true);
                gui.getCell()[a][b].setLinkedShip(ship);
            }
        }
        for (int i = 0; i < ship.getLocation().size(); i++) {
            int a = (int) ship.getLocation().get(i).getX();
            int b = (int) ship.getLocation().get(i).getY();
            if (checkValid(a, b) && outOfBounds) {
                gui.getCell()[a][b].setError(true);
                gui.getCell()[a][b].setLinkedShip(ship);
            }
        }
        selectedShip = null;
    }

    /**
     * Plaziert alle Schiffe, die sich auf dem Spielfeld befinden neu, damit Daniel das Spiel nicht kaputt machen kann
     */
    public void replaceShips() {
        Ship holdShip = selectedShip;
        ArrayList<Ship> shipsOnField = new ArrayList<>();
        for (ShipPanel[] shipPanels : gui.getCell()) {
            for (ShipPanel shipPanel : shipPanels) {
                Ship holdShips = shipPanel.getLinkedShip();
                if (holdShips != null) {
                    shipsOnField.add(holdShips);
                }
            }
        }
        for (Ship ship : shipsOnField) {
            placeShip((int) ship.getLocation().get(0).getX(), (int) ship.getLocation().get(0).getY(), ship);
        }

        selectedShip = holdShip;
    }

    public void changeOrientation(int x, int y) {
        if (selectedShip != null) {
            deletePreview();
            selectedShip.changeOrientation();
            showPreview(x, y);
        }
    }

    private void removeShip(Ship ship) {
        for (int i = 0; i < ship.getBlockedZone().size(); i++) {
            int a = (int) ship.getBlockedZone().get(i).getX();
            int b = (int) ship.getBlockedZone().get(i).getY();
            if (checkValid(a, b)) {
                gui.getCell()[a][b].setBlocked(false);
            }
        }

        for (int i = 0; i < ship.getLocation().size(); i++) {
            int a = (int) ship.getLocation().get(i).getX();
            int b = (int) ship.getLocation().get(i).getY();
            if (checkValid(a, b)) {
                gui.getCell()[a][b].setBelegt(false);
                gui.getCell()[a][b].setLinkedShip(null);
                selectedShip = null;
            }
        }
    }

    public void updateGamefield() {
        replaceShips();
        for (ShipPanel[] shipPanels : gui.getCell()) {
            for (ShipPanel shipPanel : shipPanels) {
                if (!shipPanel.isBelegt() && !shipPanel.isBlocked()) {
                    shipPanel.setBackground(Color.black);
                } else if (shipPanel.isBlocked()) {
                    shipPanel.setBackground(Color.gray);
                } else if (shipPanel.isError()) {
                    shipPanel.setBackground(Color.red);
                } else {
                    shipPanel.setBackground(Color.green);
                }
            }
        }
        gui.revalidate();
    }

    public void showPreview(int x, int y) {
        if (selectedShip != null) {
            selectedShip.setLocation(x, y);

            for (int i = 0; i < selectedShip.getLocation().size(); i++) {
                int a = (int) selectedShip.getLocation().get(i).getX();
                int b = (int) selectedShip.getLocation().get(i).getY();
                if (checkValid(a, b)) {
                    if ((gui.getCell()[a][b].isBelegt() || gui.getCell()[a][b].isBlocked())) {
                        gui.getCell()[a][b].setBackground(Color.red);
                    } else {
                        gui.getCell()[a][b].setBackground(Color.green);
                    }
                } else {
                    placementError();
                    break;
                }
            }

        }
    }

    public void placementError() {
        for (int i = 0; i < selectedShip.getLocation().size(); i++) {
            int a = (int) selectedShip.getLocation().get(i).getX();
            int b = (int) selectedShip.getLocation().get(i).getY();
            if (checkValid(a, b)) {
                gui.getCell()[a][b].setBackground(Color.red);
            }
        }
    }

    public void deletePreview() {
        updateGamefield();
    }

    public void selectShip(Ship ship) {
        selectedShip = ship;
        System.out.println("Selected Ship: " + selectedShip.getName());
    }

    public boolean checkValid(int x, int y) {
        return x >= 0 && x < gui.getCell().length && y >= 0 && y < gui.getCell()[0].length;
    }

    public boolean checkError() {
        for (ShipPanel[] shipPanels : gui.getCell()) {
            for (ShipPanel shipPanel : shipPanels) {
                if (shipPanel.isError()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Ship getSelectedShip() {
        return selectedShip;
    }

    public Ship getShip(int index) {
        return control.getShip(index);
    }

    public void clickHostGame() {
        try {
            Server server = new Server();
            control.setServer(server);
            new Thread(() -> {
                try {
                    server.startServer(control);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Clients connected");
                assert control.getClients().size() == 2;

                while (!control.getClientHandlers().stream().allMatch(ClientHandler::isReady)) ;

                System.out.println("clients Ready");


            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        gui.goToGameScreen();
    }

    public void clickJoinGame() {
        gui.goToClientScreen();
    }

    private boolean checkIP(String ip) {
        int count = 0;
        if ((!Pattern.matches("[a-zA-Z]+", ip)) && ip.length() > 2) {
            String[] splitString = ip.split("\\.");
            if (splitString.length <= 4) {
                for (String string : splitString) {
                    int holdInt = Integer.parseInt(string);
                    if (holdInt >= 0 && holdInt <= 255) {
                        count++;
                    }
                }
                return count == 4;
            }
        }
        return false;
    }

    public void connectToServer(String ip) {
        if (checkIP(ip)) {
            try {
                control.addClient(new Client(ip));
                gui.goToGameScreen();
            } catch (IOException e) {
                System.out.println("Server kaputt");
            }
        } else {
            System.out.println("Die IP-Adresse kann nicht verbunden werden");
        }
    }

    public void clickContinue() {
        if (selectedShip == null) {
            bestaetigt = true;
            gui.setDefaultColor(Color.green);
            try {
                control.getClientHandlers().get(0).sendMessage("ready");
                control.getClientHandlers().get(0).sendMessage(translateGamefield());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(gui,
                    "Du hast noch nicht alle Schiffe platziert",
                    "Placement Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public String translateGamefield() {
        StringBuilder translatedField = new StringBuilder();
        translatedField.append("field");
        for (ShipPanel[] cells : gui.getCell()) {
            for (ShipPanel cell : cells) {
                if(cell.isShip()){
                    translatedField.append("1");
                }else{
                    translatedField.append("0");
                }
            }
        }
        return translatedField.toString();
    }

}
