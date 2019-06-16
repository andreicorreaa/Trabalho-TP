import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;

class Janela extends JPanel {
    private static final long serialVersionUID = 1L;
    private int coordX = 122;
    private int coordY = 100;
    Image imgBackGround;
    Image imgBluePaddle;
    Image imgRedPaddle;
    Image imgPuck;

    public void setXY(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
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
            JOptionPane.showMessageDialog(this, "A imagem não pode ser carregada!\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgBackGround, 0, 0, getSize().width, getSize().height, this); //background
        g.drawImage(imgBluePaddle, coordX, coordY, 80, 50, this); //paddle blue
        // g.drawImage(imgRedPaddle, 122, 335, 80, 50, this); //paddle red
        g.drawImage(imgPuck, 135, 225, 50, 30, this); // puck
        Toolkit.getDefaultToolkit().sync();
    }
}