package SchiffeVersenken.GameObjects;

import SchiffeVersenken.util.Cell;

public class GameField {
    private Cell[][] cells;
    private String[] translate;

    public GameField() {
        cells = new Cell[10][10];
        translate = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j, " ");
            }

        }
    }

    public void printField() {
        System.out.print("\n");
        System.out.print("\t");
        for (String s : translate) {
            System.out.print(s + "\t");
        }
        for (int i = 0; i < cells.length; i++) {
            System.out.print("\n" + (i + 1) + "\t");
            for (int j = 0; j < cells[i].length; j++) {
                System.out.print("|" + cells[i][j].getContent() + "|" + "\t");
            }
        }
        System.out.print("\n");
    }

    public Cell[][] getCells() {
        return cells;
    }
}
