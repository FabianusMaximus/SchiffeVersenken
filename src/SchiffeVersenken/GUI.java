package SchiffeVersenken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private Control control;
    private JPanel field;
    private JButton[][] cell;
    private Container cp;
    private JLabel label;

    private Point pos = new Point();
    private int move;

    public GUI(Control control) {
        this.control = control;
        cp = this.getContentPane();
        cp.setLayout(null);

        setTitle("Schiffeversenken - GUI");
        setSize(600, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        field = new JPanel();
        field.setBackground(Color.blue);
        Dimension dimension = new Dimension();
        field.setBounds(this.getWidth() / 9, this.getHeight() / 8,
                (int) (this.getWidth() / 1.5), (int) (this.getHeight() / 1.4));
        field.setLayout(new GridLayout(10, 10, 5, 5));
        cp.add(field);

        cell = new JButton[10][10];

        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[i].length; j++) {
                cell[i][j] = new JButton();
                cell[i][j].setBackground(Color.black);
                int finalX = i;
                int finalY = j;
                cell[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (cell[finalX][finalY].getBackground() == Color.black) {
                            showValid(finalX, finalY);
                            pos.setLocation(finalX, finalY);
                            move++;
                            cell[finalX][finalY].setBackground(Color.green);
                        }
                    }
                });
                field.add(cell[i][j]);
            }
        }


        setVisible(true);
    }

    /**
     * schaut wo der nächste Move hingeht
     *
     * @param x
     * @param y
     * @return 0 = Waagerecht, 1 = Senkrecht, 2 = Fehler
     */
    private int getNextMove(int x, int y) {
        if (move != 0) {
            if (pos.getY() == y + 1 || pos.getY() == y - 1) {
                return 0;
            } else if (x == pos.getX() + 1 || x == pos.getX() - 1) {
                return 1;
            }
        }
        return 2;

    }

    private void showValid(int x, int y) {
        for (JButton[] jButtons : cell) {
            for (JButton jButton : jButtons) {
                if (jButton.getBackground() != Color.green) {
                    jButton.setBackground(Color.gray);
                }
            }
        }
        for (int i = x; i >= 0; i--) {
            if (cell[i][y].getBackground() != Color.green && getNextMove(x, y) == 1 || move == 0) {
                cell[i][y].setBackground(Color.black);
            }
            if (cell[x][i].getBackground() != Color.green && getNextMove(x, y) == 0 || move == 0) {
                cell[x][i].setBackground(Color.black);
            }
        }
        for (int i = x; i < 10; i++) {
            if (cell[x][i].getBackground() != Color.green && getNextMove(x, y) == 0 || move == 0) {
                cell[i][y].setBackground(Color.black);
            }
            if (cell[x][i].getBackground() != Color.green && getNextMove(x, y) == 0 || move == 0) {
                cell[x][i].setBackground(Color.black);
            }
        }
    }

    public static void main(String[] args) {
        new GUI(new Control());
    }
}
