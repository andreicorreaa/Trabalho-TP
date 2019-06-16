import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientSocket {
    static PrintStream os = null;
    Socket socket;
    Scanner is;
    Boolean isConnected = false;

    ClientSocket() {

        try {
            this.socket = new Socket("127.0.0.1", 80);
            os = new PrintStream(this.socket.getOutputStream(), true);
            this.is = new Scanner(this.socket.getInputStream());
            this.isConnected = true;
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host.");
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to host");
        }

        // try {

        // do {
        // // is.println("Mouse X " + coordX + "Y: " + coordY);
        // System.out.println("Token: " + is.next());
        // } while (isConnected);

        // } catch (UnknownHostException e) {
        // System.err.println("Trying to connect to unknown host: " + e);
        // } catch (IOException e) {
        // System.err.println("IOException: " + e);
        // }
    }

    public String receiveMessageFromServer() {
        return "Token: " + is.next();
    }

    public void sendMessageToServer(String message) {
        try {
            os.println(message);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public Boolean isConnected() {
        return isConnected;
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