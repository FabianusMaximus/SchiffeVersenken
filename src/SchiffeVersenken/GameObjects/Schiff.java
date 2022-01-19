package SchiffeVersenken.GameObjects;

public class Schiff {
    protected String name;
    protected int value;
    protected int groesse;

    protected Schiff(String pName, int pValue, int pGroesse){
        name = pName;
        value = pValue;
        groesse = pGroesse;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setValue(int value) {
        this.value = value;
    }

    protected void setGroesse(int groesse) {
        this.groesse = groesse;
    }

    protected String getName() {
        return name;
    }

    protected int getValue() {
        return value;
    }

    protected int getGroesse() {
        return groesse;
    }
}
