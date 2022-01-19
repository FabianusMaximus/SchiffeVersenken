package SchiffeVersenken.GameObjects;

public class GameField {
    private String[][] field;
    private String[] translate;

    public GameField() {
        field = new String[10][10];
        translate = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (int i = 0; i <field.length ; i++) {
            for (int j = 0; j < field[i].length ; j++) {
                field[i][j] = " ";
            }

        }
    }

    public void printField() {
        System.out.print("\n");
        System.out.print("\t");
        for (int i = 0; i < translate.length; i++) {
            System.out.print(translate[i] + "\t");
        }
        for (int i = 0; i < field.length; i++) {
            System.out.print("\n" + (i + 1) + "\t");
            for (int j = 0; j < field[i].length; j++) {
                System.out.print("|" + field[i][j] + "|" + "\t");
            }
        }
        System.out.print("\n");

    }
}