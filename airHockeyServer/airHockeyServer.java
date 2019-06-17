import java.net.*;
import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

class airHockeyServer {
  public static void main(String[] args) {
    ServerSocket serverSocket = null;

    try {
      serverSocket = new ServerSocket(80);
    } catch (IOException e) {
      System.out.println("Could not listen on port: " + 80 + ", " + e);
      System.exit(1);
    }

    for (int i = 0; i < 3; i++) {
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

class Servindo implements Runnable {
  Socket clientSocket;
  static PrintStream os[] = new PrintStream[3];
  static int cont = 0;
  Scanner is;
  String playerAPos = "122,100";
  String playerBPos = "122,322";

  Servindo(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void start() {
    Thread MainThread = new Thread(this, "mainThread");
    MainThread.start();
  }

  public void run() {
    try {
      Thread myThread = Thread.currentThread();
      this.is = new Scanner(clientSocket.getInputStream());
      os[cont++] = new PrintStream(clientSocket.getOutputStream());
      String inputLine, outputLine;
      inputLine = is.nextLine();

      if (inputLine.matches("(.*)P(.*)")) {
        System.out.println(myThread);
        for (int i = 0; i < cont; i++) {
          os[i].println(i);
          os[i].flush();
        }
      }

      do {
        StringTokenizer s = new StringTokenizer(is.nextLine(), ",");
        String query = s.nextToken();
        String opponent = s.nextToken();

        if (query.matches("(.*)getOpponent(.*)")) {
          if (opponent.matches("(.*)0(.*)")) {
            os[1].println(playerAPos);
          } else if (opponent.matches("(.*)1(.*)")) {
            os[0].println(playerBPos);
          }
        }


        if (query.matches("(.*)setPos(.*)")) {
          if (opponent.matches("(.*)0(.*)")) {
            String coordX = s.nextToken();
            String coordY = s.nextToken();
            playerAPos = coordX + ","+ coordY;
            os[0].println(playerAPos);
          } else if (opponent.matches("(.*)1(.*)")) {
            String coordX = s.nextToken();
            String coordY = s.nextToken();
            playerBPos = coordX + ","+ coordY;;
            os[1].println(playerBPos);
          }
        }
      } while (cont<3);

      for (int i = 0; i < cont; i++) {
        os[i].close();
        is.close();
        clientSocket.close();
      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (NoSuchElementException e) {
      System.out.println("Conexacao terminada pelo cliente");
    }
  }
};
