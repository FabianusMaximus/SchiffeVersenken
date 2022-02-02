package SchiffeVersenken.Components;

import javax.swing.*;

public class ShipPanel extends JPanel {
    private int id;
    private boolean belegt;

    public ShipPanel(){
        belegt = false;
    }

    public int getId() {
        return id;
    }

    public boolean isBelegt() {
        return belegt;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setBelegt(boolean belegt) {
        this.belegt = belegt;
    }
}
