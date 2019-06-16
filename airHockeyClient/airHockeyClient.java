import java.awt.*;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.StringTokenizer;

class airHockeyClient extends JFrame implements MouseMotionListener, Runnable {

    private static final long serialVersionUID = 1L;
    static PrintStream os = null;
    private static int coordX = 122;
    private static int coordY = 100;
    private boolean connex = true;
    Janela janela;
    ClientSocket client = null;

    public void mouseMoved(MouseEvent e) {
        // coordX = e.getX();
        // coordY = e.getY();
        try {
            if (this.client.isConnected()) {
                this.client.sendMessageToServer("I"); //serialVersionUID + "," + coordX + "," + coordY
                StringTokenizer s = new StringTokenizer(this.client.receiveMessageFromServer(),",");
                coordX = Integer.parseInt(s.nextToken());
                coordY = Integer.parseInt(s.nextToken());
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        janela.setXY(coordX, coordY);
    }

    public void mouseDragged(MouseEvent e) {
        connex = false;
    }

    private void initConnex() {
        try {
            client = new ClientSocket();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    airHockeyClient() {
        super("Air-Hockey 3000");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        janela = new Janela();
        add(janela);
        addMouseMotionListener(this);
        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void run() {
        initConnex();
    }

    static public void main(String[] args) {
        new Thread(new airHockeyClient()).start();
    }
}