import java.io.*;

import java.net.*;

import java.util.*;


public class NetServer {
  public static double xPos; // xPosition der weissen Kugel
  public static double yPos; // yPosition der weissen Kugel
  public static double xVek; //  x Vektor der Kugel
  public static double yVek; //  y Vektor der Kugel
  public static ServerSocket serverSocket;
  public static Socket client; // Client Socket
  public static String spieler1; // Spieler 1
  public static String spieler2; // Spieler 2
  public static int typ = 1;
  public static int aktiverSpieler; // der zurzeit aktive Spieler
  public static boolean erhalten = false;
  public static boolean gesendet = false;
  private static int port = 1764;

  public NetServer() throws IOException {
    // erzeugt den Server
    serverSocket = new ServerSocket(port);

    System.out.println("Bitte warten auf Mitspieler.");
    client = serverSocket.accept();

    if (!client.isConnected()) {
      System.out.println("Bitte warten auf Mitspieler.");
    }
  }

  static void empfangeDaten(Socket socket) throws IOException {
    // empf�ngt die Daten vom Client
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    char[] buffer = new char[1000];
    int anzahlZeichen = bufferedReader.read(buffer, 0, 1000);

    // blockiert bis Nachricht empfangen
    String datenmenge = new String(buffer, 0, anzahlZeichen);

    int i = 0;
    StringTokenizer st = new StringTokenizer(datenmenge);

    while (st.hasMoreTokens()) {
      i++;

      String tok = st.nextToken();

      try {
        if (i == 1) {
          xVek = Double.valueOf(tok).doubleValue();
        }

        if (i == 2) {
          yVek = Double.valueOf(tok).doubleValue();
        }
      } catch (NumberFormatException e) {
      }
    }
  }

  static void schickeDaten(Socket socket, String daten)
                    throws IOException {
    // verschickt die Daten
    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    printWriter.print(daten);
    printWriter.flush();
  }

  public static void empfangeNamen(Socket socket) throws IOException {
    // empf�ngt den Namen
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    char[] buffer = new char[10];
    int anzahlZeichen = bufferedReader.read(buffer, 0, 10);

    // blockiert bis Nachricht empfangen
    String uebertragung = new String(buffer, 0, anzahlZeichen);
    spieler2 = uebertragung;
  }

  public static void empfangeWeissePos(Socket socket) throws IOException {
    // Position der weissen Kugel empfangen
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    char[] buffer = new char[1000];
    int anzahlZeichen = bufferedReader.read(buffer, 0, 1000);

    // blockiert bis Nachricht empfangen
    String datenmenge = new String(buffer, 0, anzahlZeichen);

    int i = 0;
    StringTokenizer st = new StringTokenizer(datenmenge);

    while (st.hasMoreTokens()) {
      i++;

      String tok = st.nextToken();

      try {
        if (i == 1) {
          xPos = Double.valueOf(tok).doubleValue();
        }

        if (i == 2) {
          yPos = Double.valueOf(tok).doubleValue();
        }
      } catch (NumberFormatException e) {
      }
    }
  }

  public static final String getMeineIP() {
    try {
      return InetAddress.getLocalHost().toString();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }

    return null;
  } // getMyIP
}
