package SchiffeVersenken.GameObjects;

public class Ship {
    private String name;
    private int value;
    private int groesse;

    public Ship(String pName, int pValue, int pGroesse){
        name = pName;
        value = pValue;
        groesse = pGroesse;
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
