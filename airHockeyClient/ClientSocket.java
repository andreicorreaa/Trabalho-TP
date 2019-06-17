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
    int opponent;
    // private int player;

    ClientSocket() {
        this.tryConnect();
        os.println("P?");
        // player = 0; //Integer.parseInt(is.next());
        // System.out.println(player);
    }

    // public int getPlayer() {
    // return player;
    // }

    synchronized public void tryConnect() {
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

    synchronized public Socket returnSocket() {
        return this.socket;
    }

    synchronized public String receiveMessageFromServer() {
        return is.nextLine();
    }

    synchronized public void sendMessageToServer(String message) {
        try {
            os.println(message);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    synchronized public Boolean isConnected() {
        return socket.isConnected();
    }

    synchronized public String getOpponentXY(int opponent) {
        this.sendMessageToServer("getOpponent," + opponent);
        String resp = is.nextLine();
        return resp;
    }

    synchronized public String setPos(int player, int x, int y) {
        this.sendMessageToServer("setPos," + player + "," + x + "," + y);
        String resp = is.nextLine();
        return resp;
    }

    synchronized public void encerrarConn() {
        try {
            os.close();
            is.close();
            socket.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}