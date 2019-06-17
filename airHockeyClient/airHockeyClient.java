import java.awt.*;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;

class airHockeyClient extends JFrame implements MouseMotionListener, Runnable {

    private static final long serialVersionUID = 1L;
    static PrintStream os = null;
    private static int coordX = 122;
    private static int coordY = 100;
    private boolean connex = true;
    Janela janela;
    ClientSocket client = null;
    private int player;
    

    public void mouseMoved(MouseEvent e) {
        coordX = e.getX();
        coordY = e.getY();
        try {
            this.client.sendMessageToServer(coordX + "," + coordY); // 
            StringTokenizer s = new StringTokenizer(this.client.receiveMessageFromServer(), ",");
            player = Integer.parseInt(s.nextToken());
            coordX = Integer.parseInt(s.nextToken());
            coordY = Integer.parseInt(s.nextToken());
        } catch (Exception ex) {
            System.out.println("mouseMoved: " + ex);
        }
        System.out.println(player+","+coordX + "," + coordY);
        janela.setXY(coordX, coordY, player);
    }

    public void mouseDragged(MouseEvent e) {
        connex = false;
    }

    airHockeyClient() {
        super("Air-Hockey 3000");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        janela = new Janela();
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImg, new Point(0, 0), "blank cursor");
        janela.setCursor(blankCursor);
        add(janela);
        addMouseMotionListener(this);
        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void run() {
        try {
            client = new ClientSocket();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Problema de conexão\n", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    static public void main(String[] args) {
        new Thread(new airHockeyClient()).start();
    }
}