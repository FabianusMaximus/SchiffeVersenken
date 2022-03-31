package SchiffeVersenken.Fenster;

import SchiffeVersenken.Components.*;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private GUIControl guiControl;
    private int width;
    private int height;

    private StartPanel startPanel;
    private GamePanel gamePanel;
    private ClientPanel clientPanel;
    private PlayPanel playPanel;

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

        startPanel = new StartPanel(width, height, guiControl);
        startPanel.setBounds(0, 0, width, height);
        cp.add(startPanel);

        clientPanel = new ClientPanel(width, height, guiControl);
        clientPanel.setBounds(0, 0, width, height);
        clientPanel.setVisible(false);
        cp.add(clientPanel);

        gamePanel = new GamePanel(width, height, guiControl);
        gamePanel.setBounds(0, 0, width, height);
        gamePanel.setVisible(false);
        cp.add(gamePanel);

        playPanel = new PlayPanel(width, height, guiControl);
        playPanel.setBounds(0,0,width,height);
        playPanel.setVisible(false);
        cp.add(playPanel);

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

    public ShipPanel[][] getPlayField(){
        return playPanel.getPlayerCell();
    }

    public void goToGameScreen() {
        startPanel.setVisible(false);
        clientPanel.setVisible(false);
        playPanel.setVisible(false);
        gamePanel.setVisible(true);
    }

    public void goToClientScreen() {
        startPanel.setVisible(false);
        gamePanel.setVisible(false);
        playPanel.setVisible(false);
        clientPanel.setVisible(true);

    }

    public void goToPlayScreen() {
        startPanel.setVisible(false);
        gamePanel.setVisible(false);
        clientPanel.setVisible(false);
        playPanel.updateShipStatus(gamePanel.getCell());
        playPanel.updatePlayerPanel();
        playPanel.setVisible(true);
    }

    public PlayPanel getPlayPanel(){
        return playPanel;
    }

    public void setGuiControl(GUIControl guiControl) {
        this.guiControl = guiControl;
    }

    public Font getStandardFont() {
        return standardFont;
    }

    public void setDefaultColor(Color color) {
        gamePanel.setDefaultColor(color);
    }

    public void updateActiveplacer(boolean activePlayer){
        playPanel.changePlayerTurn(activePlayer);
    }

}
