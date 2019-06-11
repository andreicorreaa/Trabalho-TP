import java.awt.*;

import javax.swing.*;

class Main extends JFrame{
    
    private static final long serialVersionUID = 1L;

    Main(){
        super("Air-Hockey 3000");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new Janela());
        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    static public void main(String[] args) {
        new Main();
    }
}