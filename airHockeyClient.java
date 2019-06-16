import java.awt.*;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

class airHockeyClient extends JFrame  implements Runnable, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    static PrintStream os = null;
    private static int coordX = 122;
    private static int coordY = 100;
    private boolean connex = true;
    Janela janela;

    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse entered: X:" + e.getX() + "Y: " + e.getY());
        coordX = e.getX();
        coordY = e.getY();
        try {
            os.println("X"+coordX+"Y"+coordY);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        janela.setXY(coordX, coordY);
        // repaint();
    }

    public void mouseDragged(MouseEvent e) {
        connex = false;
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
        Socket socket = null;
        Scanner is = null;

        try {
            socket = new Socket("127.0.0.1", 80);
            os = new PrintStream(socket.getOutputStream(), true);
            is = new Scanner(socket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host.");
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to host");
        }

        try {

            do {
                //os.println("Mouse X " + coordX + "Y: " + coordY);
            } while (connex);

            os.close();
            is.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }

    static public void main(String[] args) {
        new Thread(new airHockeyClient()).start();
    }
}