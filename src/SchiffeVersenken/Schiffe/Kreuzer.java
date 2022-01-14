package SchiffeVersenken.Schiffe;

public class Kreuzer extends Schiff{
    private static int anzKreuzer;

    public Kreuzer(){
        super("Kreuzer" , 4, 4);
        anzKreuzer++;
    }
}
