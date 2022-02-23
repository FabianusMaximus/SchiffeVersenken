package SchiffeVersenken.Components;

import SchiffeVersenken.Fenster.GUIControl;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends CustomPanel {

    private JPanel field, selectionPanel;
    private SelectionLabel[] selectShips;
    private ShipPanel[][] cell;
    private JLabel label;

    private Font standardFont;

    public GamePanel(int width, int height, GUIControl guiControl) {
        super();
        this.width = width;
        this.height = height;
        this.guiControl = guiControl;

        this.setLayout(null);

        standardFont = new Font("Times new Roman", Font.PLAIN, width / 50);

        field = new JPanel();
        field.setBackground(Color.blue);
        field.setSize((int) (height / 1.5), (int) (height / 1.5));
        field.setLocation(width / 4 - field.getWidth() / 2, height / 2 - field.getHeight() / 2);
        field.setLayout(new GridLayout(10, 10, 5, 5));
        this.add(field);

        selectionPanel = new JPanel();
        selectionPanel.setSize((int) (height / 1.5), (int) (height / 1.5));
        selectionPanel.setLocation(width / 3 + selectionPanel.getWidth() / 2,
                height / 2 - selectionPanel.getHeight() / 2);
        selectionPanel.setLayout(new GridLayout(2, 2, 10, 10));
        this.add(selectionPanel);

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
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            guiControl.clickCell(finalX, finalY);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            guiControl.changeOrientation(finalX, finalY);
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
                        guiControl.deletePreview();
                    }
                });
                field.add(cell[i][j]);
            }
        }
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

    public JLabel getLabel() {
        return label;
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

    public void setLabel(JLabel label) {
        this.label = label;
    }
}
