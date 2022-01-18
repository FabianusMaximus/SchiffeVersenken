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
                int finalJ = j;
                int finalI = i;
                cell[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cell[finalI][finalJ].setBackground(Color.green);
                    }
                });
                field.add(cell[i][j]);
            }
        }


        setVisible(true);
    }

    public static void main(String[] args) {
        new GUI(new Control());
    }
}
