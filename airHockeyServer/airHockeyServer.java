import java.net.*;
import java.io.*;
import java.util.*;

class airHockeyServer {
  public static void main(String[] args) {
    ServerSocket serverSocket=null;

    try {
      serverSocket = new ServerSocket(80);
    } catch (IOException e) {
      System.out.println("Could not listen on port: " + 80 + ", " + e);
      System.exit(1);
    }

    for (int i=0; i<3; i++) {
      Socket clientSocket = null;
      try {
        clientSocket = serverSocket.accept();
      } catch (IOException e) {
        System.out.println("Accept failed: " + 80 + ", " + e);
        System.exit(1);
      }

      System.out.println("Accept Funcionou!");

      new Servindo(clientSocket).start();

    }

    try {
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}


class Servindo extends Thread {
  Socket clientSocket;
  static PrintStream os[] = new PrintStream[3];
  static int cont=0;
  String initCoordX = "122";
  String initCoordY = "100";
  Scanner is;

  Servindo(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void run() {
    try {
      this.is = new Scanner(clientSocket.getInputStream());
      os[cont++] = new PrintStream(clientSocket.getOutputStream());
      String inputLine, outputLine;

      do {
        inputLine = is.nextLine();
        for (int i=0; i<cont; i++) {
          System.out.println(inputLine);
          if (inputLine.equals("I")) {
            os[i].println(this.returnInitialCoords());
          }
          // os[i].println(i + inputLine);
          os[i].flush();
        }
      } while (!inputLine.equals("exit"));

      for (int i=0; i<cont; i++)
        os[i].close();
      is.close();
      clientSocket.close();

    } catch (IOException e) {
      e.printStackTrace();
    } catch (NoSuchElementException e) {
      System.out.println("Conexacao terminada pelo cliente");
    }
  }

  public String returnInitialCoords() {
    return initCoordX+","+initCoordY;
  }
};
