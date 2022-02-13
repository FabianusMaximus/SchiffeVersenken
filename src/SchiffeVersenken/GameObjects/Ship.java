package SchiffeVersenken.GameObjects;

import java.awt.*;
import java.util.ArrayList;

public class Ship {
    private String name;
    private int value;
    private int groesse;
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
        if (orientation == Orientation.HORIZONTAL) {
            for (int i = 0; i < groesse + 2; i++) {
                holdY =+ i;
                blockedIndexes.add(new Point(holdX, holdY));
            }
        } else {
            for (int i = 0; i < groesse + 2; i++) {
                blockedIndexes.add(new Point(holdX + i, holdY));

            }
        }
        return blockedIndexes;
    }

    public void changeOrientation() {
        orientation = orientation.flip();
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

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getGroesse() {
        return groesse;
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
