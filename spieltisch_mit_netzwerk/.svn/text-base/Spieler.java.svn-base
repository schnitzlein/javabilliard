public class Spieler {
  private String name;
  private String ipadresse;
  private String kugelfarbe;
  private boolean status; // true wenn der Spieler aktiv am spielen ist
  private boolean gefoult;
  private int foul = 0;
  private int foulHintereinander = 0;
  private int anzahlStoesse = 0;
  private int anzahlEingelocht = 0; //anzahl der eigenen eingelochten Kugeln
  private int gewinnRunden = 0;

  // Konstruktor der Klasse Spieler, keine Parameter
  public Spieler() {
    name = "";
    ipadresse = null;
    kugelfarbe = "";
  }

  // erhoeht die Anzahl der gewonnen Runden
  public void setGewinnRunden() {
    gewinnRunden++;
  }

  // wirft Anzahl gewonnener Runden zurueck
  public int getGewinnRunden() {
    return gewinnRunden;
  }

  // wirft Spieler Aktivitaet zurueck
  public boolean getStatus() {
    return status;
  }

  // setzt Spieler Aktivitaet true|false
  public void setStatus(boolean b) {
    status = b;
  }

  // setzt den Spieler Namen
  public void setName(String n) {
    name = n;
  }

  // wirft den Spieler Namen zurueck
  public String getName() {
    return name;
  }

  // wirft die Ip des Spielers zurueck
  public String getIP() {
    return ipadresse;
  }

  //setzt die Ip des Spielers
  public void setIP(String ip) {
    ipadresse = ip;
  }

  //setzt die Kugelfarbe des Spielers
  public void setKugelfarbe(String farbe) {
    kugelfarbe = farbe;
  }

  //wirft die Anzahl der Fouls zurueck
  public int getFoul() {
    return foul;
  }

  // inkrementiert die Anzahl der Fouls
  public void setFoul() {
    foul++;
  }

  public void resetFoul() {
    foul = 0;
  }

  // inkrementiert die Anzahl der Fouls
  public void setFoulHintereinander() {
    foulHintereinander++;
  }

  public int getFoulHintereinander() {
    return foulHintereinander;
  }

  public void resetFoulHinereinander() {
    foulHintereinander = 0;
  }

  public int getAnzahlStoesse() {
    return anzahlStoesse;
  }

  // inkrementiert die Anzahl der Stoesse
  public void setAnzahlStoesse() {
    anzahlStoesse++;
  }

  public int getAnzahlEingelocht() {
    return anzahlEingelocht;
  }

  // inkrementiert die Anzahl der eingelochten Kugeln
  public void setAnzahlEingelocht() {
    anzahlEingelocht++;
  }

  //wirft die Kugelfarbe zurueck
  public String getKugelfarbe() {
    return kugelfarbe;
  }

  // wirft aus ob im letzten zug gefoult wurde
  public boolean getGefoult() {
    return gefoult;
  }

  // setzt das im letzten zug gefoult wurde
  public void setGefoult(boolean b) {
    gefoult = b;
  }

  // Wenn Kugel eingelocht, index der eingelochten Kugel übergeben
  /* intere Liste des Spielers, wenn keine Kugel mehr auf dem Spielfeld ist
   * muss die schwarze Kugel eingelocht werden, prüfung erforderlich.
   * Falls kugelliste[n] für alle n false, keine mehr auf dem tisch
   * oder andere lösung...
   */
}
