package SchiffeVersenken;

import SchiffeVersenken.Schiffe.Schiff;

import java.util.ArrayList;

public class Player {
    private ArrayList<Schiff> schiffe = new ArrayList<>();

    public Player(){

    }

    public void addShips(Schiff ship){
        schiffe.add(ship);
    }
}
