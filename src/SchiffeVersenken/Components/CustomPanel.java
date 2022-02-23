package SchiffeVersenken.Components;

import SchiffeVersenken.Fenster.GUIControl;

import javax.swing.*;

public abstract class CustomPanel extends JPanel {
    protected int width, height;
    protected GUIControl guiControl;

    public CustomPanel(int width, int height , GUIControl guiControl){
        this.width = width;
        this.height = height;
        this.guiControl = guiControl;
    }

}
