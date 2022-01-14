package SchiffeVersenken.Schiffe;

public class Zerstörer extends Schiff{
    private static int anzZerstörer;

    public Zerstörer(){
        super("Zerstörer" , 3 , 3);
        anzZerstörer++;
    }
}
