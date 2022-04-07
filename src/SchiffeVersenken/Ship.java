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

    private ArrayList<Point> generateBlockedZone(int x, int y) {
        ArrayList<Point> blockedIndexes = new ArrayList<Point>();
        int holdX = x - 1;
        int holdY = y - 1;

        for (int j = 0; j < groesse + 2; j++) {
            if (orientation == Orientation.HORIZONTAL
                    && checkBounds(holdX, holdY + j)) {
                blockedIndexes.add(new Point(holdX, holdY + j));
            } else if (orientation == Orientation.VERTICAL
                    && checkBounds(holdX + j, holdY)) {
                blockedIndexes.add(new Point(holdX + j, holdY));
            }
        }

        blockedIndexes.add(new Point(x, holdY));
        blockedIndexes.add(new Point(x, y +groesse));

        for (int j = 0; j < groesse + 2; j++) {
            if (orientation == Orientation.HORIZONTAL
                    && checkBounds(holdX + 2, holdY + j)) {
                blockedIndexes.add(new Point(holdX + 2, holdY + j));
            } else if (orientation == Orientation.VERTICAL
                    && checkBounds(holdX + j, holdY + 2)) {
                blockedIndexes.add(new Point(holdX + j, holdY + 2));
            }
        }

        return blockedIndexes;
    }

    private boolean checkBounds(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
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
