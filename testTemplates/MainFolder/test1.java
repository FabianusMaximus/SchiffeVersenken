package MainFolder;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class DragMouseAdapter extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();
        TransferHandler handler = c.getTransferHandler();
        handler.exportAsDrag(c, e, TransferHandler.COPY);
    }
}

public class test1 {
    public static void main(String[] args) {
        JFrame f = new JFrame("Drag & Drop");
        ImageIcon icon1 = new ImageIcon("C:\\Users\\danie\\Desktop\\Test\\src\\MainFolder\\cyan.png");
        Image image1 = icon1.getImage();
        Image newimage1 = image1.getScaledInstance(100,100, Image.SCALE_SMOOTH);
        icon1 = new ImageIcon(newimage1);
        ImageIcon icon2 = new ImageIcon("C:\\Users\\danie\\Desktop\\Test\\src\\MainFolder\\green.png");
        Image image2 = icon2.getImage();
        Image newimage2 = image2.getScaledInstance(50,50, Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(newimage2);
        JPanel panel = new JPanel(new GridLayout(2, 1));
        JPanel panel1 = new JPanel(new BorderLayout());
        JPanel panel2 = new JPanel();
        panel1.setBackground(Color.red);
        panel2.setBackground(Color.white);
        JLabel label1 = new JLabel(icon1);
        JLabel label2 = new JLabel(icon2);
        MouseListener listener = new DragMouseAdapter();
        label1.addMouseListener(listener);
        label2.addMouseListener(listener);
        label1.setTransferHandler(new TransferHandler("icon"));
        label2.setTransferHandler(new TransferHandler("icon"));
        panel1.add(label1);
        panel2.add(label2);
        panel.add(panel1);
        panel.add(panel2);
        f.setLayout(new GridLayout(1, 2));
        f.setSize(300, 400);
        f.add(panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}