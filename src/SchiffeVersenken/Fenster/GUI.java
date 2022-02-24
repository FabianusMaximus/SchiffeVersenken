package SchiffeVersenken.Fenster;

import SchiffeVersenken.Components.GamePanel;
import SchiffeVersenken.Components.SelectionLabel;
import SchiffeVersenken.Components.ShipPanel;
import SchiffeVersenken.Components.StartPanel;
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

    private StartPanel startPanel;
    private GamePanel gamePanel;

    private Container cp;
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

        //TODO hier muss noch der Start screen hinzugefügt werden und die weiteren Schritte müssen auch noch entworfen werden

        startPanel = new StartPanel(width, height, guiControl);
        startPanel.setBounds(0, 0, width, height);
        cp.add(startPanel);


        gamePanel = new GamePanel(width, height, guiControl);
        gamePanel.setBounds(0, 0, width, height);
        gamePanel.setVisible(false);
        cp.add(gamePanel);

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

    public ShipPanel[][] getCell() {
        return gamePanel.getCell();
    }

    public void goToGameScreen() {
        startPanel.setVisible(false);
        gamePanel.setVisible(true);
    }

    public Container getCp() {
        return cp;
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

    public void setCp(Container cp) {
        this.cp = cp;
    }

    public void setStandardFont(Font standardFont) {
        this.standardFont = standardFont;
    }

    public Font getStandardFont() {
        return standardFont;
    }
}
