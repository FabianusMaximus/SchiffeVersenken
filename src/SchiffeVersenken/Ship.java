package SchiffeVersenken;

import java.awt.*;
import java.util.ArrayList;

public class Ship {
    private String name;
    private int value;
    private int groesse;
    private ArrayList<Point> location;
    private ArrayList<Point> blockedZone;
    private Orientation orientation;

    public Ship(String pName, int pGroesse) {
        name = pName;
        value = pGroesse;
        groesse = pGroesse;
        orientation = Orientation.HORIZONTAL;

    }

    public ArrayList<Point> applyOrientation(int x, int y) {
        ArrayList<Point> nextIndexes = new ArrayList<Point>();
        if (orientation == Orientation.HORIZONTAL) {
            for (int i = 0; i < groesse; i++) {
                nextIndexes.add(new Point(x, y + i));
            }
        } else {
            for (int i = 0; i < groesse; i++) {
                nextIndexes.add(new Point(x + i, y));

            }
        }
        return nextIndexes;

    }

    public ArrayList<Point> generateBlockedZone(int x, int y) {
        ArrayList<Point> blockedIndexes = new ArrayList<Point>();
        int holdX = x - 1;
        int holdY = y - 1;
        

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < groesse + 2; j++) {

                if (orientation == Orientation.HORIZONTAL
                        && holdX + i >= 0 && holdX + i < 10 && holdY + j >= 0 && holdY + j < 10) {
                    blockedIndexes.add(new Point(holdX + i, holdY + j));
                } else if (orientation == Orientation.VERTICAL
                        && holdX + j >= 0 && holdX + j < 10 && holdY + i >= 0 && holdY + i < 10){
                    //TODO hier stimmt was nicht wirklich so wirklich wirklich nicht
                    blockedIndexes.add(new Point(holdX + j, holdY + i));
                }
            }
        }
        return blockedIndexes;
    }

    public void changeOrientation() {
        orientation = orientation.flip();
    }

    public void setBlockedZone(int x, int y) {
        blockedZone = generateBlockedZone(x, y);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setGroesse(int groesse) {
        this.groesse = groesse;
    }

    public void setLocation(int x, int y) {
        location = applyOrientation(x, y);
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getGroesse() {
        return groesse;
    }

    public ArrayList<Point> getLocation() {
        return location;
    }

    public ArrayList<Point> getBlockedZone() {
        return blockedZone;
    }
}

enum Orientation {
    HORIZONTAL, VERTICAL;

    Orientation flip() {
        if (this.equals(HORIZONTAL)) {
            return VERTICAL;
        }
        return HORIZONTAL;
    }
}
