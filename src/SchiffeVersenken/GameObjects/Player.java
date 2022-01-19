package SchiffeVersenken.GameObjects;

import java.util.ArrayList;

public class Player {
    private ArrayList<Ship> schiffe = new ArrayList<>();

    public Player(){

    }

    public void addShips(Ship ship){
        schiffe.add(ship);
    }
}
