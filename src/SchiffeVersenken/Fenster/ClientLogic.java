package SchiffeVersenken.Fenster;

import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.Network.Client;
import SchiffeVersenken.Network.ClientHandler;
import SchiffeVersenken.Ship;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClientLogic {
    private Client client;
    private GUI gui;
    private Ship selectedShip;
    private boolean bestaetigt = false;
    private boolean activePlayer = false;

    public ClientLogic(Client client, GUI gui) {
        this.client = client;
        this.gui = gui;
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
    private void replaceShips() {
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
            GUIControl.applyColorSheme(shipPanels);
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

    private void placementError() {
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

    private boolean checkError() {
        for (ShipPanel[] shipPanels : gui.getCell()) {
            for (ShipPanel shipPanel : shipPanels) {
                if (shipPanel.isError()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Ship getSelectedShip() {
        return selectedShip;
    }

    public void clickContinue() {
        if (selectedShip == null && !checkError()) {
            bestaetigt = true;
            gui.setDefaultColor(Color.green);
            try {
                client.sendMessage(translateGamefield());
                System.out.println("Field translation: " + translateGamefield());
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

    private String translateGamefield() {
        StringBuilder translatedField = new StringBuilder();
        translatedField.append("field");
        for (ShipPanel[] cells : gui.getCell()) {
            for (ShipPanel cell : cells) {
                if (cell.isShip()) {
                    translatedField.append("1");
                } else {
                    translatedField.append("0");
                }
            }
        }
        return translatedField.toString();
    }

    public void shotRoutine(int iD){
        try {
            if (activePlayer){
                client.sendMessage("shot:" + iD);
            }else {
                JOptionPane.showMessageDialog(gui,
                        "Du bist nicht an der Reihe, warte bis der Gegner seinen Zug gemacht hat",
                        "Ungedulds Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActivePlayer(boolean activePlayer) {
        System.out.println("Du bisch dran: " + activePlayer);
        this.activePlayer = activePlayer;
        gui.updateActiveplacer(activePlayer);
    }

}
