package SchiffeVersenken;


import SchiffeVersenken.Fenster.GUIControl;
import SchiffeVersenken.Network.ClientHandler;
import SchiffeVersenken.Network.Server;
import SchiffeVersenken.Network.ServerLogic;

import java.util.ArrayList;

public class Control {
    private GUIControl guiControl;
    private Ship[] ships;

    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Server server;

    private ServerLogic serverLogic;

    private boolean gewonnen;

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

    public void addClientHandler(ClientHandler clientHandler) {
        this.clientHandlers.add(clientHandler);
    }

    public ArrayList<ClientHandler> getClientHandlers() {
        return this.clientHandlers;
    }

    public void setServerLogic() {
        serverLogic = new ServerLogic(clientHandlers.get(0), clientHandlers.get(1), server);
    }

    public ServerLogic getServerLogic() {
        return serverLogic;
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

    public void setGewonnen(boolean gewonnen) {
        this.gewonnen = gewonnen;
    }

    public void start() {
        openGUI();
    }

    public void restart() {
        guiControl.closeClient();
        clientHandlers = new ArrayList<>();
        if(server != null){
            server.close();
        }
        ships = null;
        server = null;
        serverLogic = null;
        gewonnen = false;
        guiControl = null;

        generateShips();
        openGUI();
    }
}
