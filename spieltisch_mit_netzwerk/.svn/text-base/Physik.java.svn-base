import java.lang.Math.*;

import java.util.*;


/**
* Die Klasse Physik enthŠlt sŠmtliche physikalische und mathematische Methoden,
* die eingesetzt werden.
*
* Hier werden die Objekte auf Kollision geprüft,die Kollisionen werden
* behandelt, die Bewegung der Kugeln berechnet und das einlochen der Kugel
* durchgeführt.
* ZusŠtlich enthŠlt die Klasse die benötigten mathematischen Methoden
*/
public class Physik {
  // Anfang Attribute
  double x1;

  // Anfang Attribute
  double x2 = 0;
  double y1;
  double y2 = 0;
  double vx1;
  double vx2 = 0;
  double vy1;
  double vy2 = 0;
  Punkt s1 = new Punkt(0, 0);
  Punkt s2 = new Punkt(0, 0);
  Punkt n = new Punkt(0, 0);
  Punkt p1 = new Punkt(0, 0);
  Punkt p2 = new Punkt(0, 0);
  double winkel1 = 0;
  double winkel2 = 0;
  int aufreihenS1 = 260;
  int aufreihenS2 = 260;
  double bremsfaktorTisch = 0.989;
  double bremsfaktorBande = 0.8;
  double bremsfaktorKugel = 1;
  double niedrigstgeschwindigkeit = 0.25;
  static ArrayList<Kugel> dings;
  Regelwerk r = new Regelwerk();

  // Ende Attribute
  public Physik() {
    dings = SpielTisch.getKugelListe();

    //lokal = SpielTisch.s1;
  }

  // Anfang Methoden

  /**
  * Die Kugel wird bewegt, indem der die Werte des Richtungsvektors auf
  * die Werte der Position aufaddiert werden
  */
  public void bewege(Kugel k) {
    Punkt altpos = k.getKugelPos();
    Punkt richtungsvektor = k.getKugelVektor();
    k.setKugelPos(new Punkt(altpos.x + richtungsvektor.x,
                            altpos.y + richtungsvektor.y));
  }

  /**
  * Prüfung auf- und Behandlung von Kollision zweier Kugeln
  *
  * Zwei Kugeln werden überprüft, ob der Abstand zueinander kleiner ist als
  * ihr doppelter Radius und somit eine Kollison besteht.
  * Trift das zu, wird die Kollision gleich behandelt, indem den beiden Kugeln
  * die entsprechenden neuen Richtungsvektoren zugeteilt werden.
  */
  public void pruefeKollisionKugeln(int i, int j) {
    if ((abstandBerechnung(dings.get(i).getKugelPos(),
                             dings.get(j).getKugelPos()) <= 20) && (i != j) &&
          ((Math.sqrt((dings.get(i).getKugelVektor().x * //und keine der kugeln geschwindigkeit 0 hat
                        dings.get(i).getKugelVektor().x) +
                        (dings.get(i).getKugelVektor().y * dings.get(i)
                                                                  .getKugelVektor().y)) > 0.01) ||
            (Math.sqrt((dings.get(j).getKugelVektor().x * dings.get(j)
                                                                 .getKugelVektor().x) +
                         (dings.get(j).getKugelVektor().y * dings.get(j)
                                                                   .getKugelVektor().y)) > 0.01))) {
      ruecksetzen(i, j);

      // stossnormale
      n = vektorSub(dings.get(i).getKugelPos(), dings.get(j).getKugelPos());
      winkel1 = (vektorSkalar(dings.get(i).getKugelVektor(), n) / vektorSkalar(n,
                                                                               n));
      //parallele Komponente
      p1 = vektorMulZahl(winkel1, n);
      //senkrechte Komponente
      s1 = vektorSub(dings.get(i).getKugelVektor(), p1);
      //Winkel zwischen Kugel2 und Stoßnormale
      winkel2 = vektorSkalar(dings.get(j).getKugelVektor(), n) / vektorSkalar(n,
                                                                              n);
      //Parallelkomponente
      p2 = vektorMulZahl(winkel2, n);
      //senkrechte Komponente
      s2 = vektorSub(dings.get(j).getKugelVektor(), p2);
      dings.get(i).setKugelVektor(vektorAdd(s1, p2));
      dings.get(j).setKugelVektor(vektorAdd(s2, p1));

      //s.kugelnKollidieren();
      if (SpielTisch.kugelAngespielt == false) { //prüft für den ersten anstoß, ob es die eigene farbe ist
        r.pruefeRichtigAngestossen(i);
      }

      SpielTisch.kugelAngespielt = true;
    }
  }

  /**
  * Kollision mit Bande wird geprüft, indem die Position der Kugel mit den festen Werten der Banden verglichen wird.
  */
  public void pruefeKollisionBande(int i) {
    //oben
    if (dings.get(i).getKugelPos().y < 40) {
      if (((dings.get(i).getKugelPos().x > 55) &&
            (dings.get(i).getKugelPos().x < 360)) //oben links
             ||((dings.get(i).getKugelPos().x > 420) &&
                 (dings.get(i).getKugelPos().x < 725))) { //oben rechts
        dings.get(i)
             .setKugelVektor(new Punkt((dings.get(i).getKugelVektor().x * bremsfaktorBande),
                                       (dings.get(i).getKugelVektor().y * -1 * bremsfaktorBande)));
        dings.get(i).setKugelPos(new Punkt(dings.get(i).getKugelPos().x, 40)); // bei Kollision setzt kurz hinter bande
      }
    }

    // unten
    if ((dings.get(i).getKugelPos().y > 380) &&
          (dings.get(i).getKugelPos().y < 440)) {
      if (((dings.get(i).getKugelPos().x > 55) &&
            (dings.get(i).getKugelPos().x < 360)) //unten links
             ||((dings.get(i).getKugelPos().x > 420) &&
                 (dings.get(i).getKugelPos().x < 725))) { //unten rechts
        dings.get(i)
             .setKugelVektor(new Punkt((dings.get(i).getKugelVektor().x * bremsfaktorBande),
                                       (dings.get(i).getKugelVektor().y * -1 * bremsfaktorBande)));
        dings.get(i).setKugelPos(new Punkt(dings.get(i).getKugelPos().x, 380));
      }
    }

    // links
    if ((dings.get(i).getKugelPos().x < 40) &&
          ((dings.get(i).getKugelPos().y > 55) &&
            (dings.get(i).getKugelPos().y < 365))) {
      dings.get(i)
           .setKugelVektor(new Punkt((dings.get(i).getKugelVektor().x * -1 * bremsfaktorBande),
                                     (dings.get(i).getKugelVektor().y * bremsfaktorBande)));
      dings.get(i).setKugelPos(new Punkt(40, dings.get(i).getKugelPos().y));
    }

    // rechts
    if ((dings.get(i).getKugelPos().x > 740) &&
          ((dings.get(i).getKugelPos().y > 55) &&
            (dings.get(i).getKugelPos().y < 365))) {
      dings.get(i)
           .setKugelVektor(new Punkt((dings.get(i).getKugelVektor().x * -1 * bremsfaktorBande),
                                     (dings.get(i).getKugelVektor().y * bremsfaktorBande)));
      dings.get(i).setKugelPos(new Punkt(740, dings.get(i).getKugelPos().y));
    }
  }

  /**
  * Prüft und behandelt Ereignisse im Taschenraum
  *
  * prüft ob die kugel in einem taschenbereich ist, indem der festgelegte
  * taschenbereichradius mit der position
  * der Kugel verglichen wird.(für jede tasche)
  * ist die kugel im taschenbereich, wird dann erst (um rechenaufwand zu sparen)
  * geprüft, ob die kugel eingeloch,
  * eine der ecken triff oder mit der taschenbande kollidiert
  */
  public void pruefeTasche(int i, int k) {
    //tasche 1
    if ((abstandBerechnung(SpielTisch.taschen.get(k), dings.get(i).getKugelPos())) <= 35) {
      //pruefeLoch
      if ((abstandBerechnung(SpielTisch.taschen.get(k),
                               dings.get(i).getKugelPos())) <= 17) {
        //vorlŠufig wird die kugel aus dem spiel gesetzt
        eingelocht(i);

        //
      } //if pruefeLoch

      //pruefe ecke1
      if (abstandBerechnung(SpielTisch.ecken.get(2 * k),
                              dings.get(i).getKugelPos()) <= 10) {
        //kollision ecke
        //stossnormale
        n = vektorSub(dings.get(i).getKugelPos(), SpielTisch.ecken.get(2 * k));
        winkel1 = (vektorSkalar(dings.get(i).getKugelVektor(), n) / vektorSkalar(n,
                                                                                 n));
        //parallele Komponente
        p1 = vektorMulZahl(winkel1, n);
        p2 = vektorMulZahl(-1, p1);
        //senkrechte Komponente
        s1 = vektorSub(dings.get(i).getKugelVektor(), p1);
        dings.get(i).setKugelVektor(vektorAdd(s1, p2));
      }

      //pruefe ecke2
      if (abstandBerechnung(SpielTisch.ecken.get((2 * k) + 1),
                              dings.get(i).getKugelPos()) <= 10) {
        //kollision ecke
        //stossnormale
        n = vektorSub(dings.get(i).getKugelPos(),
                      SpielTisch.ecken.get((2 * k) + 1));
        winkel1 = (vektorSkalar(dings.get(i).getKugelVektor(), n) / vektorSkalar(n,
                                                                                 n));
        //parallele Komponente
        p1 = vektorMulZahl(winkel1, n);
        p2 = vektorMulZahl(-1, p1);
        //senkrechte Komponente
        s1 = vektorSub(dings.get(i).getKugelVektor(), p1);
        dings.get(i).setKugelVektor(vektorAdd(s1, p2));
      }

      /*
      * innenwandfunktionswerte
      *  +----------------------------------------+
      *  |                   / \                  |
      *  |                 0/   \ 1               |
      *  |                                        |
      *  |                                        |
      *  |                                        |
      *  |                                        |
      *  |                                        |
      *  |                 \     /                |
      *  |                3 \   / 2               |
      *  +----------------------------------------+
      * a  = anstieg
      * a0 = -1.17
      * a1 =  1.17
      * a2 = -1.17
      * a3 =  1.17
      *
      * b  = schnittpunkt mit y achse
      * b0 = 487
      * b1 =-447
      * b2 = 887
      * b3 = -47
      */
      if (k == 1) { //innenwŠnde der 1.tasche
        x1 = (dings.get(i).getKugelPos().x) + 10;
        y1 = (dings.get(i).getKugelPos().y) + 10;

        if ((y1 < ((x1 * (-1.17)) + 487)) && (x1 >= 377) && (x1 <= 400)) { // if y<x*a0+b0 und im wertebereich
                                                                           //stossnormale fest definiert
          n = new Punkt(-35, -30);
          winkel1 = (vektorSkalar(dings.get(i).getKugelVektor(), n) / vektorSkalar(n,
                                                                                   n));
          //parallele Komponente
          p1 = vektorMulZahl(winkel1, n);
          p2 = vektorMulZahl(-1, p1);
          //senkrechte Komponente
          s1 = vektorSub(dings.get(i).getKugelVektor(), p1);
          dings.get(i).setKugelVektor(vektorAdd(s1, p2));
        }

        if ((y1 < ((x1 * (1.17)) - 447)) && (x1 >= 400) && (x1 <= 423)) { //if y<x*a1+b1 und im wertebereich
                                                                          //stossnormale fest definiert
          n = new Punkt(35, -30);
          winkel1 = (vektorSkalar(dings.get(i).getKugelVektor(), n) / vektorSkalar(n,
                                                                                   n));
          //parallele Komponente
          p1 = vektorMulZahl(winkel1, n);
          p2 = vektorMulZahl(-1, p1);
          //senkrechte Komponente
          s1 = vektorSub(dings.get(i).getKugelVektor(), p1);
          dings.get(i).setKugelVektor(vektorAdd(s1, p2));
        }
      } //if(k==1)

      if (k == 4) { //innenwŠnde der 5.tasche
        x1 = (dings.get(i).getKugelPos().x) + 10;
        y1 = (dings.get(i).getKugelPos().y) + 10;

        if ((y1 > ((x1 * (-1.17)) + 887)) && (x1 >= 400) && (x1 <= 423) &&
              (y1 < 440)) { // if y>x*a2+b2 und im wertebereich
                            //stossnormale fest definiert
          n = new Punkt(35, 30);
          winkel1 = (vektorSkalar(dings.get(i).getKugelVektor(), n) / vektorSkalar(n,
                                                                                   n));
          //parallele Komponente
          p1 = vektorMulZahl(winkel1, n);
          p2 = vektorMulZahl(-1, p1);
          //senkrechte Komponente
          s1 = vektorSub(dings.get(i).getKugelVektor(), p1);
          dings.get(i).setKugelVektor(vektorAdd(s1, p2));
        }

        if ((y1 > ((x1 * (1.17)) - 47)) && (x1 >= 377) && (x1 <= 400) &&
              (y1 < 440)) { //if y>x*a3+b3 und im wertebereich
                            //stossnormale fest definiert
          n = new Punkt(-35, 30);
          winkel1 = (vektorSkalar(dings.get(i).getKugelVektor(), n) / vektorSkalar(n,
                                                                                   n));
          //parallele Komponente
          p1 = vektorMulZahl(winkel1, n);
          p2 = vektorMulZahl(-1, p1);
          //senkrechte Komponente
          s1 = vektorSub(dings.get(i).getKugelVektor(), p1);
          dings.get(i).setKugelVektor(vektorAdd(s1, p2));
        }
      } //if(k==4)

      /*
      * falls eine kugel mit zu hoher geschwindigkeit an der prüfung vorbeihüpft,
      * wird sie hier mit zusŠtzlichen rŠndern zurück ins spiel gesetzt;
      */
      x1 = (dings.get(i).getKugelPos().x);
      y1 = (dings.get(i).getKugelPos().y);

      if ((dings.get(i).getKugelPos().x) < 30) {
        dings.get(i).setKugelPos(new Punkt(40, y1));
      }

      if ((dings.get(i).getKugelPos().y) < 20) {
        dings.get(i).setKugelPos(new Punkt(x1, 40));
      }

      if ((dings.get(i).getKugelPos().x) > 750) {
        dings.get(i).setKugelPos(new Punkt(740, y1));
      }

      if (((dings.get(i).getKugelPos().y) > 400) &&
            ((dings.get(i).getKugelPos().y) < 430)) {
        dings.get(i).setKugelPos(new Punkt(x1, 380));
      }
    }
  }

  /**
  * Kugel wird vom Spielfeld genommen und unterhalb dessen aufgereit
  */
  public void eingelocht(int i) {
    SpielTisch.setKugelEingelocht(true);

    if (SpielTisch.getErstesEinlochen()) { //wenn die allererste kugel versenkt wird

      if (i < 7) { //wenn sie rot ist

        if (SpielTisch.s1.getStatus()) { // wenn s1 aktiv
          SpielTisch.s1.setKugelfarbe("rot"); //wird s1 rot
          SpielTisch.s2.setKugelfarbe("gelb");
        } else {
          SpielTisch.s2.setKugelfarbe("rot"); //sonst wird s2 rot
          SpielTisch.s1.setKugelfarbe("gelb");
        }
      }

      if ((i > 6) && (i < 14)) { //das selbe in gelb

        if (SpielTisch.s1.getStatus()) {
          SpielTisch.s1.setKugelfarbe("gelb");
          SpielTisch.s2.setKugelfarbe("rot");
        } else {
          SpielTisch.s2.setKugelfarbe("gelb");
          SpielTisch.s1.setKugelfarbe("rot");
        }
      }

      SpielTisch.setErstesEinlochen(false);

      if (i == 14) {
        SpielTisch.setErstesEinlochen(true);
      }

      if (i == 15) {
        SpielTisch.setErstesEinlochen(true);
      }
    }

    //    prüfeRegelnEingelocht(i);
    if (i == 14) {
      dings.get(i).setKugelVektor(new Punkt(0, 0));
      dings.get(i).setKugelPos(new Punkt(209, 6666));
    } else {
      boolean sps = SpielTisch.s1.getStatus();
      ; //SpielTisch.s1.getStatus(); //hier müsste man den status von spieler 1 rauskrigen. kA krieg ich jetzt irgendwie nicht hin..

      if ((sps == true) || (SpielTisch.spielModus == "training")) {
        dings.get(i).setKugelVektor(new Punkt(0, 0));
        dings.get(i).setKugelPos(new Punkt(aufreihenS1, 520));
        aufreihenS1 += 22;
      } else {
        dings.get(i).setKugelVektor(new Punkt(0, 0));
        dings.get(i).setKugelPos(new Punkt(aufreihenS2, 545));
        aufreihenS2 += 22;
      }
    }

    if (SpielTisch.spielModus != "training") {
      r.pruefeRichtigEingelocht(i);
    }
  }

  /**
  * Die Kugeln werden nach dem Feststellen einer Kollision wieder in die
  * entegengesetzte Richtung auseinandergeschoben, um eine bestehende
  * Überschneidung zu Lösen.
  */
  public void ruecksetzen(int i, int j) {
    while ((abstandBerechnung(dings.get(i).getKugelPos(),
                                dings.get(j).getKugelPos()) <= 20) //solange die kugeln sich überschneiden
              &&((Math.sqrt((dings.get(i).getKugelVektor().x * //und keine der kugeln geschwindigkeit 0 hat
                              dings.get(i).getKugelVektor().x) +
                              (dings.get(i).getKugelVektor().y * dings.get(i)
                                                                        .getKugelVektor().y)) > 0.01) ||
                  (Math.sqrt((dings.get(j).getKugelVektor().x * dings.get(j)
                                                                       .getKugelVektor().x) +
                               (dings.get(j).getKugelVektor().y * dings.get(j)
                                                                         .getKugelVektor().y)) > 0.01))) {
      x1 = dings.get(i).getKugelPos().x;
      x2 = dings.get(j).getKugelPos().x;
      y1 = dings.get(i).getKugelPos().y;
      y2 = dings.get(j).getKugelPos().y;
      vx1 = dings.get(i).getKugelVektor().x;
      vx2 = dings.get(j).getKugelVektor().x;
      vy1 = dings.get(i).getKugelVektor().y;
      vy2 = dings.get(j).getKugelVektor().y;

      x1 -= (0.05 * vx1);
      y1 -= (0.05 * vy1);

      x2 -= (0.05 * vx2);
      y2 -= (0.05 * vy2);

      Punkt p1 = new Punkt(x1, y1);
      Punkt p2 = new Punkt(x2, y2);
      dings.get(i).setKugelPos(p1);
      dings.get(j).setKugelPos(p2);
    } //while
  }

  /*
  * unterschreitet eine Kugel die mindestgeschwindigkeit,
  * wird sie angehalten
  */
  public void kugelAnhalten(int i) {
    if (Math.sqrt((dings.get(i).getKugelVektor().x * dings.get(i)
                                                            .getKugelVektor().x) +
                    (dings.get(i).getKugelVektor().y * dings.get(i)
                                                              .getKugelVektor().y)) < niedrigstgeschwindigkeit) {
      dings.get(i).setKugelVektor(new Punkt(0, 0));
    } //if
  }

  /**
  * Abstand zweier Punkte wird über den Satz des Phytagoras ermittelt.
  */
  public double abstandBerechnung(Punkt p1, Punkt p2) {
    double a = Math.sqrt(((p2.x - p1.x) * (p2.x - p1.x)) +
                         ((p2.y - p1.y) * (p2.y - p1.y)));

    return a;
  }

  /**
  * Vektoraddition für zwei Punkte
  */
  public Punkt vektorAdd(Punkt p1, Punkt p2) {
    Punkt p = new Punkt(0, 0);
    p.x = p1.x + p2.x;
    p.y = p1.y + p2.y;

    return p;
  }

  /**
  * Vektoraddition für zwei Punkte
  */
  public Punkt vektorSub(Punkt p1, Punkt p2) {
    Punkt p = new Punkt(0, 0);
    p.x = p1.x - p2.x;
    p.y = p1.y - p2.y;

    return p;
  }

  /**
  * Vektormultiplikation von einem Punkt mit einer Zahl
  */
  public Punkt vektorMulZahl(double zahl, Punkt p2) {
    Punkt p = new Punkt(0, 0);
    p.x = zahl * p2.x;
    p.y = zahl * p2.y;

    return p;
  }

  /**
  * Vektordivision für zwei Punkte
  */
  public Punkt vektorDiv(Punkt p1, Punkt p2) {
    Punkt p = new Punkt(0, 0);
    p.x = p1.x / p2.x;
    p.y = p1.y / p2.y;

    return p;
  }

  /**
  * Skalarprodukt zweier Punkte
  */
  public double vektorSkalar(Punkt p1, Punkt p2) {
    return ((p1.x * p2.x) + (p1.y * p2.y));
  }

  // Ende Methoden
}
