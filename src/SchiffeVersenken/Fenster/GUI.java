package SchiffeVersenken.Fenster;

import SchiffeVersenken.Components.SelectionLabel;
import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.Control;
import SchiffeVersenken.GameObjects.Ship;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
    private GUIControl guiControl;
    private int width;
    private int height;
    private JPanel field;
    private JPanel selectionPanel;
    private SelectionLabel[] selectShips;
    private ShipPanel[][] cell;
    private Container cp;
    private JLabel label;

    private Point pos = new Point();
    private int move;


    private Font standardFont;

    public GUI(GUIControl guiControl) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) ((int) size.getWidth() - size.getWidth() / 5);
        height = (int) ((int) size.getHeight() - size.getHeight() / 5);
        this.guiControl = guiControl;
        cp = this.getContentPane();
        cp.setLayout(null);

        standardFont = new Font("Times new Roman", Font.PLAIN, width / 50);

        setTitle("Schiffeversenken - GUI");
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        field = new JPanel();
        field.setBackground(Color.blue);
        field.setSize((int) (height / 1.5), (int) (height / 1.5));
        field.setLocation(width / 4 - field.getWidth() / 2, height / 2 - field.getHeight() / 2);
        field.setLayout(new GridLayout(10, 10, 5, 5));
        cp.add(field);

        selectionPanel = new JPanel();
        selectionPanel.setSize((int) (height / 1.5), (int) (height / 1.5));
        selectionPanel.setLocation(width / 3 + selectionPanel.getWidth() / 2, height / 2 - selectionPanel.getHeight() / 2);
        selectionPanel.setLayout(new GridLayout(2, 2, 10, 10));
        cp.add(selectionPanel);

        selectShips = new SelectionLabel[5];
        String[] names = {"Schlachtschiff", "Kreuzer", "Zerstörer", "U-Bot", "U-Bot"};
        for (int i = 0; i < selectShips.length; i++) {
            selectShips[i] = new SelectionLabel(names[i], guiControl.getShip(i));
            selectShips[i].setBorder(new LineBorder(Color.black));
            selectShips[i].setHorizontalAlignment(SwingConstants.CENTER);
            selectShips[i].setVerticalAlignment(SwingConstants.CENTER);
            selectShips[i].setFont(standardFont);
            int finalI = i;
            selectShips[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    guiControl.selectShip(selectShips[finalI].getLinkedShip());
                    selectionPanel.remove(selectShips[finalI]);
                }
            });
            selectShips[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    selectShips[finalI].setForeground(Color.lightGray);
                }
            });
            selectShips[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    selectShips[finalI].setForeground(Color.black);
                }
            });
            selectionPanel.add(selectShips[i]);
        }

        cell = new ShipPanel[10][10];
        int id = 0;
        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[i].length; j++) {
                cell[i][j] = new ShipPanel(id++);
                cell[i][j].setBackground(Color.black);
                int finalX = i;
                int finalY = j;
                cell[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println(e.getButton());
                        if (e.getButton() == MouseEvent.BUTTON1){
                            guiControl.clickCell(finalX, finalY);
                        }else if (e.getButton() == MouseEvent.BUTTON3){
                            guiControl.changeOrientation(finalX,finalY);
                        }

                    }
                });
                cell[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        guiControl.showPreview(finalX, finalY);
                    }
                });
                cell[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseExited(MouseEvent e) {
                        guiControl.deletePreview(finalX, finalY);
                    }
                });
                field.add(cell[i][j]);
            }
        }


        setVisible(true);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public JPanel getField() {
        return field;
    }

    public JPanel getSelectionPanel() {
        return selectionPanel;
    }

    public SelectionLabel[] getSelectShips() {
        return selectShips;
    }

    public ShipPanel[][] getCell() {
        return cell;
    }

    public Container getCp() {
        return cp;
    }

    public JLabel getLabel() {
        return label;
    }

    public Point getPos() {
        return pos;
    }

    public int getMove() {
        return move;
    }

    public void setGuiControl(GUIControl guiControl) {
        this.guiControl = guiControl;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setField(JPanel field) {
        this.field = field;
    }

    public void setSelectionPanel(JPanel selectionPanel) {
        this.selectionPanel = selectionPanel;
    }

    public void setSelectShips(SelectionLabel[] selectShips) {
        this.selectShips = selectShips;
    }

    public void setCell(ShipPanel[][] cell) {
        this.cell = cell;
    }

    public void setCp(Container cp) {
        this.cp = cp;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public void setStandardFont(Font standardFont) {
        this.standardFont = standardFont;
    }

    public Font getStandardFont() {
        return standardFont;
    }
}
