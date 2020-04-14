import java.io.*;

import java.net.*;

import java.util.*;


public class NetClient {
  public static double xPos; // xPosition der weissen Kugel
  public static double yPos; // yPosition der weissen Kugel
  public static double xVek; //  x Vektor der Kugel
  public static double yVek; //  y Vektor der Kugel
  public static Socket server; // Socket zum Server
  public static String spieler2; // Netzwerkpart
  public static boolean erhalten = false;
  public static boolean gesendet = false;
  public static boolean verbunden = false;
  private static int port = 1764;

  //stellt die Verbindung zum Server her
  public static void connect(String IP, String s2)
                      throws UnknownHostException, IOException {
    // mit dem Server verbinden
    server = new Socket(IP, port);
    spieler2 = s2;

    if (server.isConnected()) {
      verbunden = true;
    } else {
      verbunden = false;
    }

    server.setSoTimeout(100000);
  }

  // empfangen der Daten
  public static void empfangeDaten(Socket socket)
                            throws IOException, NullPointerException {
    if (socket == null) {
      throw new NullPointerException("Server nicht gefunden!");
    }

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

        erhalten = true;

        //andere Tokens werden verworfen
      } catch (NumberFormatException e) {
      }
    }
  }

  // versenden der Daten
  public static void schickeDaten(Socket socket, String daten)
                           throws IOException {
    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    printWriter.print(daten);
    printWriter.flush();
  }

  // Namen des anderen Spielers empfangen
  static void empfangeNamen(Socket socket) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    char[] buffer = new char[10];
    int anzahlZeichen = bufferedReader.read(buffer, 0, 10);
    // blockiert bis Nachricht empfangen
    spieler2 = new String(buffer, 0, anzahlZeichen);
  }

  // Position der weissen Kugel empfangen
  public static void empfangeWeissePos(Socket socket) throws IOException {
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
}
