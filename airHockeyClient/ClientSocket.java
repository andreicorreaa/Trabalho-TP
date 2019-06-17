import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JOptionPane;

public class ClientSocket {
    static PrintStream os = null;
    Socket socket;
    Scanner is;
    Boolean isConnected = false;

    ClientSocket() {
        this.tryConnec();
    }

    public void tryConnec() {
        try {
            this.socket = new Socket("127.0.0.1", 80);
            os = new PrintStream(this.socket.getOutputStream(), true);
            this.is = new Scanner(this.socket.getInputStream());
            this.isConnected = true;
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "Problema de conexão com o servidor:\n" + e + socket.isConnected());
            // System.exit(1);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Problema de conexão com o servidor:\n" + e + socket.isConnected(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            // System.exit(1);
        }
    }

    public Socket returnSocket() {
        return this.socket;
    }

    public String receiveMessageFromServer() {
        return is.next();
    }

    public void sendMessageToServer(String message) {
        try {
            os.println(message);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public String getInitialPosition() {
        this.sendMessageToServer("I");
        return is.next();
    }

    public Boolean isConnected() {
        return socket.isConnected();
    }

    public void encerrarConn() {
        try {
            os.close();
            is.close();
            socket.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}