package SchiffeVersenken.Fenster;

import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.Network.Client;

import java.awt.*;

public class PlayLogic {
    private Client client;
    private GUI gui;

    public PlayLogic(Client client, GUI gui){
        this.client = client;
        this.gui = gui;
    }

    public void updatePlayField(){
               for (ShipPanel[] shipPanels :  gui.getPlayField()) {
            GUIControl.applyColorSheme(shipPanels);
        }
        gui.revalidate();
    }

}
