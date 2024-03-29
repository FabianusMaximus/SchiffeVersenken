package SchiffeVersenken.Fenster;

import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.Control;
import SchiffeVersenken.Network.Client;
import SchiffeVersenken.Network.Server;
import SchiffeVersenken.Ship;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class GUIControl {
    private GUI gui;
    private Control control;
    private ClientLogic clientLogic;

    private int holdID; //Speichert die letzte ID, auf die geschossen wurde

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
        clientLogic.clickCell(x, y);
    }

    /**
     * Funktion die das Schiff entsprechend seiner Größe und seiner Orientierung auf dem Spielfeld platziert
     *
     * @param x    Erste Koordinate, an dem das Schiff platziert werden, soll
     * @param y    Zweite Koordinate, an dem das Schiff platziert werden, soll
     * @param ship Schiff das platziert werden soll
     */
    public void placeShip(int x, int y, Ship ship) {
        clientLogic.placeShip(x, y, ship);
    }

    /**
     * Dreht das ausgewählte Schiff um 90° und zeigt die aktualisierte Vorschau an
     *
     * @param x X-Koordinate des Klicks
     * @param y Y-Koordinate des Klicks
     */
    public void changeOrientation(int x, int y) {
        clientLogic.changeOrientation(x, y);
    }


    /**
     * Überprüft den Status jeder Zelle und färbt sie entsprechend neu ein
     */
    public void updateGamefield() {
        clientLogic.updateGamefield();
    }

    /**
     * Berechnet die Indexe, ausgehend von der Position der Maus, die eingefärbt werden müssen, um den Hover effekt
     * zu erzeugen.
     *
     * @param x X-Koordinate der Maus
     * @param y Y-Koordinate der Maus
     */
    public void showPreview(int x, int y) {
        clientLogic.showPreview(x, y);
    }


    public void deletePreview() {
        clientLogic.deletePreview();
    }

    public void selectShip(Ship ship) {
        clientLogic.selectShip(ship);
    }


    public Ship getSelectedShip() {
        return clientLogic.getSelectedShip();
    }

    public Ship getShip(int index) {
        return control.getShip(index);
    }


    public static void applyColorSheme(ShipPanel[][] field) {
        for (ShipPanel[] shipPanels : field) {
            for (ShipPanel shipPanel : shipPanels) {
                switch (shipPanel.getStatus()) {
                    case FREE -> shipPanel.setBackground(Color.black);
                    case LOADED -> shipPanel.setBackground(Color.green);
                    case ERROR -> shipPanel.setBackground(Color.red);
                    case BLOCKED -> shipPanel.setBackground(Color.gray);
                }
            }
        }
    }


    /**
     * Funktion die ausgeführt wird, wenn der Spieler "Host game" auf der Oberfläche drückt.
     * Es wird ein Server erstellt, der in einem neuen Thread läuft.
     * Gleichzeitig wird ein neuer Client erstellt, welcher sich dann direkt mit dem Server verbindet.
     * Danach wartet die Funktion bis beide Clients bestätigt haben und das Spiel fortgesetzt werden kann.
     */
    public void clickHostGame() {
        try {
            Server server = new Server();
            control.setServer(server);

            connectToServer("127.0.0.1");
            new Thread(() -> {
                try {
                    server.startServer(control);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Clients connected");

                control.setServerLogic();
                for (int i = 0; i < 2; i++) {
                    control.getClientHandlers().get(i).setServerLogic(control.getServerLogic());
                }

            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funktion die ausgeführt werden soll, wenn der Spieler "Join Game" auf der Oberfläche drückt
     */
    public void clickJoinGame() {
        gui.goToClientScreen();
    }

    /**
     * Überprüft, ob das was eingegeben wurde den Standards für eine IPV4-Adresse entspricht
     *
     * @param ip String der überprüft werden soll
     * @return Ob die Ip Adresse valide ist
     */
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

    /**
     * Funktion die einen neuen Client zu dem bestehenden Server verbindet.
     *
     * @param ip IP-Adresse, mit der sich verbunden werden soll
     */
    public void connectToServer(String ip) {
        if (checkIP(ip)) {
            try {
                Client client = new Client(ip, this);
                this.clientLogic = new ClientLogic(client, gui);
                new Thread(client::init).start();
                gui.goToGameScreen();

            } catch (IOException e) {
                System.out.println("Konnte den Server mit mit der IP-Adresse " + ip + " nicht erreichen");
                JOptionPane.showMessageDialog(gui, "Konnte den Server mit mit der IP-Adresse " + ip +
                        " nicht erreichen", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Die IP-Adresse kann nicht verbunden werden");
            JOptionPane.showMessageDialog(gui, "Die eingegebene IP-Adresse (" + ip +
                    ") ist keine Valide IP-Adresse", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Funktion die ausgeführt wird, wenn man alle seine Schiffe platziert hat und fortfahren möchte.
     * Sendet eine Nachricht an den Server und färbt den Knopf neu ein
     */
    public void clickContinue() {
        clientLogic.clickContinue();
    }

    /**
     * Bereitet das Spielfeld vor und wechselt anschließend dort hin
     */
    public void goToPlayScreen() {
        gui.goToPlayScreen();
    }

    public void shotRoutine(int iD) {
        holdID = iD;
        clientLogic.shotRoutine(iD);
    }

    public void setActivePlayer(boolean activePlayer) {
        clientLogic.setActivePlayer(activePlayer);
    }

    public void flipActivePlayer() {
        clientLogic.flipActivePlayer();
        gui.updateActiveplayer(clientLogic.getActivePlayer());
    }


    /**
     * Setzt den Status des Feldes neu, um anzuzeigen, ob der Schuss getroffen hat
     *
     * @param treffer Ob der Spieler getroffen hat
     */
    public void applyShot(boolean treffer) {
        if (treffer) {
            gui.getPlayPanel().changeEnemyCellStatus(holdID, ShipPanel.Status.HIT);
        } else {
            gui.getPlayPanel().changeEnemyCellStatus(holdID, ShipPanel.Status.MISSED);
        }
    }

    /**
     * Setzt den Status des Feldes neu, auf das der Gegner geschossen hat
     *
     * @param string String der die ID des Feldes beinhaltet
     */
    public void setEnemyShot(String string) {
        int iD = Integer.parseInt(string.split(":")[1]);
        if (gui.getPlayPanel().getPlayerCell(iD).getStatus() != ShipPanel.Status.LOADED) {
            flipActivePlayer();
        }
        gui.getPlayPanel().updateStatusPlayerPanel(iD, ShipPanel.Status.HIT);
        for (Ship ship : control.getShips()) {
            if (checkShipSunken(ship)) sinkShip(ship);
        }
        if (checkAllShipsSunken()) {
            clientLogic.sendGameOver();
            goToWinScreen(false);
        }
    }

    public boolean checkShipSunken(Ship ship) {
        int count = 0;
        for (Point point : ship.getLocation()) {
            ShipPanel aktCell = gui.getPlayPanel().getPlayerCell()[(int) point.getX()][(int) point.getY()];
            if (aktCell.getStatus() == ShipPanel.Status.HIT) {
                count++;
                if (count == ship.getGroesse()) {
                    System.out.println("Schiff's DOWN!");
                    return true;
                }
            }
        }
        return false;
    }

    public void sinkShip(Ship ship) {
        ship.setSchiffsAmArschEyyyy(true);
        for (Point point : ship.getLocation()) {
            gui.getPlayPanel().getPlayerCell()[(int) point.getX()][(int) point.getY()].setStatus(ShipPanel.Status.SUNKEN);
            gui.getPlayPanel().updatePlayerPanel();
        }
        clientLogic.sendSunken(calculateSunkenIds(ship));
    }

    private Integer[] calculateSunkenIds(Ship ship) {
        ArrayList<Integer> holdIDs = new ArrayList<>();
        for (Point point : ship.getBlockedZone()) {
            if (point.getX() >= 0 && point.getY() >= 0 && point.getX() < 10 && point.getY() < 10) {
                holdIDs.add(gui.getPlayPanel().getPlayerCell()[(int) point.getX()][(int) point.getY()].getId());
            }
        }
        return holdIDs.toArray(new Integer[0]);
    }

    public void verarbeitenSunken(String message) {
        String holdString = message.split(":")[1];
        String[] s_IDs = holdString.split(",");
        ArrayList<Integer> iDs = new ArrayList<>();
        for (String s : s_IDs) {
            iDs.add(Integer.parseInt(s));
        }
        for (Integer i : iDs) {
            gui.getPlayPanel().changeEnemyCellStatus(i, ShipPanel.Status.SUNKEN);
        }
        gui.getPlayPanel().updatePlayerPanel();
    }

    private boolean checkAllShipsSunken() {
        int count = 0;
        for (Ship ship : control.getShips()) {
            if (ship.isSchiffsAmArschEyyyy()) count++;
        }
        return count == control.getShips().length;
    }

    public void goToWinScreen(boolean gewonnen) {
        control.setGewonnen(gewonnen);
        gui.goToWinScreen(gewonnen);
    }

    public void startNewGame() {
        gui.dispose();
        control.restart();
    }

    public void closeClient() {
        clientLogic.closeClient();
    }


}
