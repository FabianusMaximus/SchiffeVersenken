package SchiffeVersenken.Fenster;

import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.Network.Client;
import SchiffeVersenken.Ship;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ClientLogic {
    private Client client;
    private GUI gui;
    private Ship selectedShip;
    private boolean bestaetigt = false;
    private boolean activePlayer = false;
    private int holdID;

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
            if (gui.getCell()[x][y].getStatus() != ShipPanel.Status.LOADED
                    && gui.getCell()[x][y].getStatus() != ShipPanel.Status.BLOCKED
                    && selectedShip != null) {
                System.out.println(gui.getCell()[x][y].getStatus());
                placeShip(x, y, selectedShip);
            } else if ((gui.getCell()[x][y].getStatus() == ShipPanel.Status.LOADED ||
                    gui.getCell()[x][y].getStatus() == ShipPanel.Status.ERROR) && selectedShip == null) {
                Ship holdShip = gui.getCell()[x][y].getLinkedShip();
                removeShip(holdShip);
                selectedShip = holdShip;
                replaceShips();
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
        for (int i = 0; i < ship.getLocation().size(); i++) {
            int a = (int) ship.getLocation().get(i).getX();
            int b = (int) ship.getLocation().get(i).getY();
            if (checkValid(a, b) && !outOfBounds) {
                if (gui.getCell()[a][b].getStatus() == ShipPanel.Status.FREE) {
                    gui.getCell()[a][b].setStatus(ShipPanel.Status.LOADED);
                } else {
                    gui.getCell()[a][b].setStatus(ShipPanel.Status.ERROR);
                }

                gui.getCell()[a][b].setLinkedShip(ship);
            } else if (checkValid(a, b) && outOfBounds) {
                gui.getCell()[a][b].setStatus(ShipPanel.Status.ERROR);
                gui.getCell()[a][b].setLinkedShip(ship);
            }
        }

        for (int i = 0; i < ship.getBlockedZone().size(); i++) {
            int a = (int) ship.getBlockedZone().get(i).getX();
            int b = (int) ship.getBlockedZone().get(i).getY();
            if (checkValid(a, b) && !outOfBounds) {
                if (gui.getCell()[a][b].getStatus() == ShipPanel.Status.LOADED) {
                    gui.getCell()[a][b].setStatus(ShipPanel.Status.ERROR);
                } else {
                    gui.getCell()[a][b].setStatus(ShipPanel.Status.BLOCKED);
                }
            }
        }

        selectedShip = null;
    }

    int count = 0;

    /**
     * Plaziert alle Schiffe, die sich auf dem Spielfeld befinden neu, damit Daniel das Spiel nicht kaputt machen kann
     */
    private void replaceShips() {
        System.out.println("replace wurde gecalled" + count++);
        Ship holdShip = selectedShip;
        ArrayList<Ship> shipsOnField = new ArrayList<>();
        for (ShipPanel[] shipPanels : gui.getCell()) {
            for (ShipPanel shipPanel : shipPanels) {
                shipPanel.setStatus(ShipPanel.Status.FREE);
                Ship holdShips = shipPanel.getLinkedShip();
                if (holdShips != null) {
                    shipsOnField.add(holdShips);
                }
            }
        }
        Ship verglShip = new Ship("Karl", 3);
        for (Ship ship : shipsOnField) {
            if (ship != verglShip) {
                verglShip = ship;
                placeShip((int) ship.getLocation().get(0).getX(), (int) ship.getLocation().get(0).getY(), ship);
            }
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
        GUIControl.applyColorSheme(gui.getCell());
        gui.revalidate();
    }

    public void showPreview(int x, int y) {
        if (selectedShip != null) {
            selectedShip.setLocation(x, y);

            for (int i = 0; i < selectedShip.getLocation().size(); i++) {
                int a = (int) selectedShip.getLocation().get(i).getX();
                int b = (int) selectedShip.getLocation().get(i).getY();
                if (checkValid(a, b)) {
                    ShipPanel shipPanel = gui.getCell()[a][b];
                    if ((shipPanel.isBelegt() || shipPanel.isBlocked())) {
                        shipPanel.setBackground(Color.red);
                    } else {
                        shipPanel.setBackground(Color.green);
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

    public void shotRoutine(int iD) {
        holdID = iD;
        try {
            if (activePlayer) {
                client.sendMessage("shot:" + iD);
            } else {
                JOptionPane.showMessageDialog(gui,
                        "Du bist nicht an der Reihe, warte bis der Gegner seinen Zug gemacht hat",
                        "Ungedulds Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendSunken(Integer[] iDs) {
        StringBuilder message = new StringBuilder();
        message.append("sunken:");
        for (int i : iDs) {
            message.append(i).append(",");
        }
        try {
            client.sendMessage(message.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendGameOver() {
        try {
            client.sendMessage("gameOver");
            //TODO vielleicht geht das ja echt
            TimeUnit.SECONDS.sleep(1);
            client.sendMessage("gameOver");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setActivePlayer(boolean activePlayer) {
        System.out.println("Du bisch dran: " + activePlayer);
        this.activePlayer = activePlayer;
        gui.updateActiveplayer(activePlayer);
    }

    public boolean getActivePlayer() {
        return activePlayer;
    }

    public void flipActivePlayer() {
        activePlayer = !activePlayer;
    }

}
