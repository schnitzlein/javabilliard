import java.util.*;


public class Regelwerk {
  Spieler tmp1 = new Spieler();
  Spieler tmp2 = new Spieler();
  String farbe;

  public void erstelleSpieler() {
    tmp1 = SpielTisch.s1;
    tmp2 = SpielTisch.s2;
  }

  public Regelwerk() {
    erstelleSpieler();
  }

  // gibt die anzahl der foul wieder
  public int getFoul(Spieler s) {
    return s.getFoul();
  }

  // prueft ob richtig eingelocht
  public void pruefeRichtigEingelocht(int id) {
    if (SpielTisch.s1.getStatus()) {
      farbe = SpielTisch.s1.getKugelfarbe();
    }

    if (SpielTisch.s2.getStatus()) {
      farbe = SpielTisch.s2.getKugelfarbe();
    }

    //rote kugeln
    if (farbe.equals("rot") && (id >= 0) && (id < 7)) {
      // gueltiges einlochen
      if (SpielTisch.s1.getStatus()) {
        SpielTisch.s1.setAnzahlEingelocht();
      }

      if (SpielTisch.s2.getStatus()) {
        SpielTisch.s2.setAnzahlEingelocht();
      }

      return;
    }

    //gelbe kugeln
    if (farbe.equals("gelb") && (id >= 7) && (id < 14)) {
      // gueltiges einlochen
      if (SpielTisch.s1.getStatus()) {
        SpielTisch.s1.setAnzahlEingelocht();
      }

      if (SpielTisch.s2.getStatus()) {
        SpielTisch.s2.setAnzahlEingelocht();
      }

      return;
    }

    if (farbe.equals("rot") && (id >= 7) && (id < 14)) {
      SpielTisch.anzeige2 = "FOUL!";
      SpielTisch.setSollFoulSetzen(true);
      SpielTisch.setSollSpielerWechseln(true);

      if (SpielTisch.s1.getStatus()) {
        SpielTisch.s2.setAnzahlEingelocht();
      }

      if (SpielTisch.s2.getStatus()) {
        SpielTisch.s1.setAnzahlEingelocht();
      }
    }

    if (farbe.equals("gelb") && (id >= 0) && (id < 7)) {
      SpielTisch.anzeige2 = "FOUL!";
      SpielTisch.setSollFoulSetzen(true);
      SpielTisch.setSollSpielerWechseln(true);

      if (SpielTisch.s1.getStatus()) {
        SpielTisch.s2.setAnzahlEingelocht();
      }

      if (SpielTisch.s2.getStatus()) {
        SpielTisch.s1.setAnzahlEingelocht();
      }
    }

    // wei§e kugel
    if (id == 14) {
      SpielTisch.setSollFoulSetzen(true);
      SpielTisch.setSollSpielerWechseln(true);
      SpielTisch.setSollKugelSetzen(true);
    }

    if (id == 15) {
      // schwarze kugel
      if (SpielTisch.s1.getStatus() &&
            (SpielTisch.s1.getAnzahlEingelocht() == 7)) {
        spielZuEnde("s1"); //wenn alle eingeloch + S -> gewonnen
      }

      if (SpielTisch.s1.getStatus() &&
            (SpielTisch.s1.getAnzahlEingelocht() < 7)) {
        spielZuEnde("s2"); // wenn S eingelocht, beovor alle anderen ->gegener Gewonnen
      }

      if (SpielTisch.s2.getStatus() &&
            (SpielTisch.s2.getAnzahlEingelocht() == 7)) {
        spielZuEnde("s2");
      }

      if (SpielTisch.s1.getStatus() &&
            (SpielTisch.s1.getAnzahlEingelocht() < 7)) {
        spielZuEnde("s1");
      }
    }

    if (SpielTisch.s1.getFoulHintereinander() >= 3) {
      spielZuEnde("s2"); //wenn foulanzahl = 3 hat gegner gewonnen
    }

    if (SpielTisch.s2.getFoulHintereinander() >= 3) {
      spielZuEnde("s1");
    }
  }

  // prueft angestossen
  public void pruefeRichtigAngestossen(int id) {
    if (SpielTisch.s1.getStatus()) {
      farbe = SpielTisch.s1.getKugelfarbe();
    }

    if (SpielTisch.s2.getStatus()) {
      farbe = SpielTisch.s2.getKugelfarbe();
    }

    //rote kugeln
    if (farbe.equals("rot") && (id >= 0) && (id < 7)) {
      // gueltiges angestossen
      SpielTisch.kugelAngespielt = true;
    }

    //gelbe kugeln
    if (farbe.equals("gelb") && (id >= 7) && (id < 14)) {
      // gueltiges angestossen
      SpielTisch.kugelAngespielt = true;
    }

    if (farbe.equals("rot") && (id >= 7) && (id < 14)) {
      SpielTisch.anzeige2 = "FOUL!";
      SpielTisch.setSollFoulSetzen(true);
      SpielTisch.setSollSpielerWechseln(true);
    }

    if (farbe.equals("gelb") && (id >= 0) && (id < 7)) {
      SpielTisch.anzeige2 = "FOUL!";
      SpielTisch.setSollFoulSetzen(true);
      SpielTisch.setSollSpielerWechseln(true);
    }

    // schwarze kugel
    if (id == 15) {
      SpielTisch.anzeige2 = "FOUL!";
      SpielTisch.setSollFoulSetzen(true);
      SpielTisch.setSollSpielerWechseln(true);
    }
  }

  public static void spielerWechseln() {
    SpielTisch.s1.setStatus(!SpielTisch.s1.getStatus());
    SpielTisch.s2.setStatus(!SpielTisch.s2.getStatus());
    SpielTisch.setSollSpielerWechseln(false);
  }

  public static void spielZuEnde(String gewinner) {
    SpielTisch.anzeige2 = "Spiel zu ende";

    if (gewinner == "s1") {
      SpielTisch.regelwerkvar = "   " + SpielTisch.s1.getName() +
                                " hat gewonnen!";
      System.out.println(SpielTisch.s1.getName() + " gewinnt");
      SpielTisch.s1.setGewinnRunden();
      SpielTisch.zeigeStatistik("Statistik");
      SpielTisch.loift = false;
      SpielTisch.queueSichtbar = false;
    }

    if (gewinner == "s2") {
      SpielTisch.regelwerkvar = "   " + SpielTisch.s2.getName() +
                                " hat gewonnen!";
      SpielTisch.s2.setGewinnRunden();
      SpielTisch.zeigeStatistik("Statistik");
      SpielTisch.loift = false;
      SpielTisch.queueSichtbar = false;
    }
  }

  public static void main(String[] args) {
  }
}
