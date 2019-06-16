import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

class Paddle {
    private static final long serialVersionUID = 1L;
    ImageIcon image;
    Paddle(String caminho) throws IOException{
        try{
            this.image = new ImageIcon(ImageIO.read(new File(caminho)));
        }catch(Exception e){

        }
    }
}