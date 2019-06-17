import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;

class airHockeyClient extends JFrame {

    private static final long serialVersionUID = 1L;
    static PrintStream os = null;
    private boolean connex = true;
    Janela janela;
    ClientSocket client = null;
    private int player;
    static int count = 0;

    airHockeyClient() {
        super("Air-Hockey 3000");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        janela = new Janela();
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        janela.setCursor(blankCursor);
        add(janela);
        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    static public void main(String[] args) {
        new airHockeyClient();
    }
}