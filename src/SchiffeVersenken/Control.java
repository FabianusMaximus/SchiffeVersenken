package SchiffeVersenken;


import SchiffeVersenken.Fenster.GUIControl;
import SchiffeVersenken.Network.ClientHandler;
import SchiffeVersenken.Network.Client;
import SchiffeVersenken.Network.Server;
import SchiffeVersenken.Network.ServerLogic;

import java.util.ArrayList;

public class Control {
    private GUIControl guiControl;
    private Ship[] ships;

    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    
    private Client client;

    private Server server;

    private ServerLogic serverLogic;

    private String datenSpielfeld;

    public Control() {
        generateShips();
    }

    private void openGUI() {
        if (guiControl == null) {
            guiControl = new GUIControl(this);
        } else {
            System.out.println("GUI already exists");
        }
    }

    private void generateShips() {
        ships = new Ship[]
                {new Ship("Schlachtschiff", 5),
                        new Ship("Kreuzer", 4), new Ship("Zerstörer", 3),
                        new Ship("U-Boot", 2), new Ship("U-Boot", 2)};
    }


    public boolean isCellValid(int x, int y) {

        return false;
    }

    public void setCellSelected(int x, int y) {

    }

    public GUIControl getGuiControl() {
        return guiControl;
    }

    public void addClientHandler(ClientHandler clientHandler) {
        this.clientHandlers.add(clientHandler);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ArrayList<ClientHandler> getClientHandlers() {
        return this.clientHandlers;
    }

    public Client getClient() {
        return this.client;
    }

    public void setServerLogic() {
        serverLogic = new ServerLogic(clientHandlers.get(0), clientHandlers.get(1), server);
    }

    public ServerLogic getServerLogic() {
        return serverLogic;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Ship getShip(int index) {
        return ships[index];
    }

    public Ship[] getShips() {
        return ships;
    }

    public void start() {
        openGUI();
    }
}
