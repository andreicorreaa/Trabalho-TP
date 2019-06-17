import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Cursor;
import java.util.StringTokenizer;

class Janela extends JPanel implements MouseMotionListener {
    private static final long serialVersionUID = 1L;
    private int coordXA = 122;
    private int coordYA = 100;
    private int coordXB = 122;
    private int coordYB = 322;
    int opponent;
    private int _player;
    Image imgBackGround;
    Image imgBluePaddle;
    Image imgRedPaddle;
    Image imgPuck;
    ClientSocket client;
    Thread th[] = new Thread[2];

    public void mouseMoved(MouseEvent e) {
        try {
            th[0] = new Thread(new myPos(e, _player, client, this));
            th[1] = new Thread(new oppPos(_player, client, this));

            th[0].start();
            th[1].start();
        } catch (Exception ex) {
            System.out.println("mouseMoved: " + ex);
        }
    }

    public void mouseDragged(MouseEvent e) {
        // os.println("exit");
    }

    synchronized public int isValidPosX(int x) {
        int _coordX = x;

        if (x < 30) {
            _coordX = 30;
        } else if (x > 288) {
            _coordX = 288;
        }

        return _coordX;
    }

    synchronized public int isValidPosY(int y) {
        int _coordY = y;

        if (y < 50) {
            _coordY = 50;
        } else if (y > 465) {
            _coordY = 465;
        }

        return _coordY;
    }

    synchronized public void setXY(int coordX, int coordY, int player) {
        if (player == 0) {
            this.coordXA = isValidPosX(coordX) - 40;
            this.coordYA = isValidPosY(coordY) - 40;
        } else if (player == 1) {
            this.coordXB = isValidPosX(coordX) - 40;
            this.coordYB = isValidPosY(coordY) - 40;
        }
        repaint();
    }

    public void run() {
        try {
            client = new ClientSocket();
            _player = Integer.parseInt(client.receiveMessageFromServer());

            if (_player == 0) {
                System.out.println("I am player A");
            } else {
                System.out.println("I am player B");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Problema de conexão\n" + ex, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    Janela() {
        try {
            setPreferredSize(new Dimension(320, 480));
            imgBackGround = ImageIO.read(new File("./imagens/background.jpg"));
            imgBluePaddle = ImageIO.read(new File("./imagens/bluepaddle.png"));
            imgRedPaddle = ImageIO.read(new File("./imagens/redpaddle.png"));
            imgPuck = ImageIO.read(new File("./imagens/puck.png"));
            addMouseMotionListener(this);
            run();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "A imagem não pode ser carregada!\n" + e, "Erro",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgBackGround, 0, 0, getSize().width, getSize().height, this); // background
        g.drawImage(imgBluePaddle, coordXA, coordYA, 80, 50, this); // paddle blue
        g.drawImage(imgRedPaddle, coordXB, coordYB, 80, 50, this); // paddle red
        g.drawImage(imgPuck, 135, 225, 50, 30, this); // puck
        Toolkit.getDefaultToolkit().sync();
    }
}

class myPos implements Runnable {
    private int coordX;
    private int coordY;
    int opponent;
    Janela _janela;
    String s1; 
    String s2;
    int player;

    myPos(MouseEvent e, int _player, ClientSocket client, Janela j) {
        player = _player;
        _janela = j;
        opponent = _player == 0 ? 1 : 0;
        coordX = e.getX();
        coordY = e.getY();
        String s = client.setPos(_player, coordX, coordY);
        StringTokenizer sToken = new StringTokenizer(s, ",");
        s1 = sToken.nextToken();
        s2 = sToken.nextToken();
        System.out.println("Antes de setarXY MainThread: " + " == " + s1 + " E "
                + " == " + s2 + "," + _player);
    }

    public void run() {
        _janela.setXY(Integer.parseInt(s1), Integer.parseInt(s2), player);
    }
}

class oppPos implements Runnable {
    private int coordX;
    private int coordY;
    int opponent;
    String str;
    StringTokenizer token;
    int s1; 
    int s2;
    Janela _janela;

    oppPos(int _player, ClientSocket client, Janela j) {
        _janela = j;
        opponent = _player == 0 ? 1 : 0;
        str = client.getOpponentXY(opponent);
        System.out.println("STRING RETORNADA PELO SERVER: " + str);
        token = new StringTokenizer(str, ",");
        s1 = Integer.parseInt(token.nextToken());
        s2 = Integer.parseInt(token.nextToken());
        System.out.println("Antes de setarXY OPONENTE:" + s1 + "," + s2 + "," + opponent);
    }

    public void run() {
        _janela.setXY(s1, s2, opponent);
    }
}