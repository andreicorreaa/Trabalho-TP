import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Cursor;

class Janela extends JPanel {
    private static final long serialVersionUID = 1L;
    private int coordXA = 122;
    private int coordYA = 100;
    private int coordXB = 122;
    private int coordYB = 430;
    Image imgBackGround;
    Image imgBluePaddle;
    Image imgRedPaddle;
    Image imgPuck;

    public int isValidPosX(int x) {
        int _coordX = x;

        if (x < 30) {
            _coordX = 30;
        } else if (x > 288) {
            _coordX = 288;
        }

        return _coordX;
    }

    public int isValidPosY(int y) {
        int _coordY = y;

        if (y < 50) {
            _coordY = 50;
        } else if (y > 465) {
            _coordY = 465;
        }

        return _coordY;
    }

    public void setXY(int coordX, int coordY, int player) {
        if (player == 0) {
            this.coordXA = isValidPosX(coordX) - 40;
            this.coordYA = isValidPosY(coordY) - 40;
        } else if (player == 1) {
            this.coordXB = isValidPosX(coordX) - 40;
            this.coordYB = isValidPosY(coordY) - 40;
        }
        repaint();
    }

    Janela() {
        try {
            setPreferredSize(new Dimension(320, 480));
            imgBackGround = ImageIO.read(new File("./imagens/background.jpg"));
            imgBluePaddle = ImageIO.read(new File("./imagens/bluepaddle.png"));
            imgRedPaddle = ImageIO.read(new File("./imagens/redpaddle.png"));
            imgPuck = ImageIO.read(new File("./imagens/puck.png"));
            // addMouseMotionListener(this);
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