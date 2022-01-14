package SchiffeVersenken.Schiffe;

public class Schlachtschiff extends Schiff{
    private static int anzSchlachtschiffe;

    public Schlachtschiff(){
        super("Schlachtschiff",5,5);
        anzSchlachtschiffe++;
    }
}
