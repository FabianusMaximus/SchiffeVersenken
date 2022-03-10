package SchiffeVersenken;


import SchiffeVersenken.Fenster.GUIControl;
import SchiffeVersenken.Network.Client;
import SchiffeVersenken.Network.Server;

public class Control {
    private GUIControl guiControl;
    private Ship[] ships;

    private Client client;
    private Server server;

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

    public void setClient(Client client){
        this.client = client;
    }

    public Client getClient(){
        return this.client;
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

    public void start() {
        openGUI();
    }
}
