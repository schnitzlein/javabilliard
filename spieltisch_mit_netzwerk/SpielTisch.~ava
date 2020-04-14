import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.InputStream;

import java.lang.Math;

import java.net.ConnectException;
import java.net.InetAddress;

import java.util.*;

import javax.swing.*;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;


public class SpielTisch extends JFrame implements MouseMotionListener,
                                                  MouseListener, Runnable,
                                                  ActionListener {
  // Variablen

  // Anfang Attribute
  private String[] images = {
                              "images/Pooltisch.gif", "images/Kugel rot.gif",
                              "images/Kugel gelb.gif", "images/Kugel weiss.gif",
                              "images/Kugel schwarz.gif", "images/queue.gif",
                              "images/anzeige.gif", "images/kraft3/kraft0.gif",
                              "images/kraft3/kraft1.gif",
                              "images/kraft3/kraft2.gif",
                              "images/kraft3/kraft3.gif",
                              "images/kraft3/kraft4.gif",
                              "images/kraft3/kraft5.gif",
                              "images/kraft3/kraft6.gif",
                              "images/kraft3/kraft7.gif",
                              "images/kraft3/kraft8.gif",
                              "images/kraft3/kraft9.gif",
                              "images/kraft3/kraft10.gif",
                            };
  private Image[] imgs = null;
  private int x = 0;
  private int y = 0;
  private int runde = 0; // eine runde endet nachdem beide spieler, gueltig oft gestossen haben
  private int zug = 0; // wie oft der queue gestossen wird
  public static String regelwerkvar = "";

  /* Liste der Kugelobjekte */
  private static ArrayList<Kugel> kugelListe = new ArrayList<Kugel>();

  /* Liste der Taschenraummittelpunkte */
  static ArrayList<Punkt> taschen = new ArrayList<Punkt>();

  /* Liste Eckpunkte */
  static ArrayList<Punkt> ecken = new ArrayList<Punkt>();
  public static String anzeige2 = "Pool Billard - Gruppe 3.4";
  public static String foulanzeigeS1 = "";
  public static String foulanzeigeS2 = "";
  double awinkel = 0;
  double schwungfaktorX = 0;
  double schwungfaktorY = 0;
  double MousePressedChecker = 0;
  double p1x = 0;
  double p1y = 0; //P1 = Punkt der weissen Kugel
  double p2x = 0;
  double p2y = 0; // P2 = Punkt des Mauszeigers
  double schwung = 0.2;
  Font kreide;
  long delta = 0; // zur Errechnung der Zeit, die für den letzten Durchlauf benštigt wurde
  long last = 0; // Speicherung der letzten Systemzeit
  long stBegin = 0; // zeit bei der der Sto§ begonnen hat
  long stEnde = 0; // zeit bei der der sto§ durchgeführt ist
  long stDelta = 0; // tatsŠchliche ausholzeit
  long stKraft = 0; // daraus berechneter
  boolean kugelnBewegenSich = false;
  static boolean loift = false;
  boolean istServer = true;
  public static boolean kugelAngespielt = true;
  public static boolean kugelEingelocht = false;
  public static boolean gestossen = false;
  static boolean queueSichtbar = false;
  static boolean erstesEinlochen = true;
  static boolean sollSpielerWechseln = false;
  static boolean kugelSetzen = false;
  static boolean sollKugelSetzen = false;
  static boolean sollFoulSetzen = false;
  static boolean posEmpfangen = false;
  public static String spielModus = "";
  String ip;
  String port;
  ImagePanel imp;

  // Ende Variablen

  // Schnittstellen
  Kugel neueKugel;
  Punkt neuerPunkt;
  Queue derQueue;
  static Spieler s1 = new Spieler();
  static Spieler s2 = new Spieler();
  Physik p = new Physik();
  NetServer s;
  NetClient c;

  // Menue Elemente
  JMenuBar menuBar;
  JMenu dateimenu;
  JMenu helpmenu;
  JMenu submenu;
  JMenuItem menuItem1;
  JMenuItem menuItem2;
  JMenuItem host;
  JMenuItem client;
  JMenuItem menuItem4;
  JMenuItem menuItem5;
  JMenuItem beenden;
  JMenuItem statistik;

  // Ende Attribute

  // Ende Schnittstellen
  public SpielTisch(String title) {
    // Frame-Initialisierung
    super(title);

    // Spieler Einstellungen
    s1.setStatus(true);

    s2.setStatus(false);

    // Menue

    // Erstellen der Menüleiste
    menuBar = new JMenuBar();

    // DateiMenü
    dateimenu = new JMenu("Spiel");
    dateimenu.setMnemonic(KeyEvent.VK_S);
    menuBar.add(dateimenu);

    // DateiMenüelemente

    // Erster Menüpunkt: Ein Untermenü
    dateimenu.addSeparator();
    submenu = new JMenu("Neues Spiel");
    submenu.setMnemonic(KeyEvent.VK_N);
    menuItem1 = new JMenuItem("Einzelspieler");
    menuItem1.setMnemonic(KeyEvent.VK_E);
    menuItem1.addActionListener(this);
    submenu.add(menuItem1);
    menuItem2 = new JMenuItem("Zweispieler Lokal");
    menuItem2.setMnemonic(KeyEvent.VK_Z);
    menuItem2.addActionListener(this);
    submenu.add(menuItem2);
    host = new JMenuItem("Netzwerkspiel starten");
    submenu.add(host);
    host.addActionListener(this);
    client = new JMenuItem("Netzwerkspiel suchen");
    submenu.add(client);
    client.addActionListener(this);
    dateimenu.add(submenu);

    // Zweiter Menüpunkt
    statistik = new JMenuItem("Statistik");
    statistik.setMnemonic(KeyEvent.VK_S);
    statistik.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
    statistik.addActionListener(this);
    dateimenu.add(statistik);

    // Dritter Menüpunkt
    beenden = new JMenuItem("Beenden");
    beenden.setMnemonic(KeyEvent.VK_B);
    beenden.setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.CTRL_MASK));
    beenden.addActionListener(this);
    dateimenu.add(beenden);

    // HelpMenü
    helpmenu = new JMenu("Hilfe");
    helpmenu.setMnemonic(KeyEvent.VK_H);
    menuBar.add(helpmenu);

    // HelpMenüelemente

    // Erster Meüpunkt
    menuItem4 = new JMenuItem("Spielanleitung");
    menuItem4.setMnemonic(KeyEvent.VK_P);
    menuItem4.addActionListener(this);
    helpmenu.add(menuItem4);

    // Zweiter Menüpunkt
    menuItem5 = new JMenuItem("About...");
    menuItem5.setMnemonic(KeyEvent.VK_A);
    menuItem5.addActionListener(this);
    helpmenu.add(menuItem5);

    this.setJMenuBar(menuBar); // Menüleiste wird dem Fenster hinzugefügt

    addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent evt) {
          System.exit(0);
        }
      });

    erstelleTisch();

    // Ende Komponenten
    neuStarten();
    setResizable(false);
    setVisible(true);

    // Anfang Komponenten
  } // Konsturktor

  // Anfang Methoden

  // erstellt das Fenster
  public void erstelleTisch() {
    setSize(806, 649);

    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);

    imp = new ImagePanel(images); // erstellt Panel um Bilder zu zeichnen

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(BorderLayout.CENTER, imp);
    getContentPane().addMouseMotionListener(this);
    getContentPane().addMouseListener(this);

    // Komponenten

    /*
    *Koordinaten der Taschenraummittelpunkte wird gefüllt
    *( auch Lochmittelpunkte )
    * jeweils -10
    */
    neuerPunkt = new Punkt(30, 30); //lo
    taschen.add(neuerPunkt);
    neuerPunkt = new Punkt(390, 25); //mo
    taschen.add(neuerPunkt);
    neuerPunkt = new Punkt(750, 30); //ro
    taschen.add(neuerPunkt);
    neuerPunkt = new Punkt(750, 390); //ru
    taschen.add(neuerPunkt);
    neuerPunkt = new Punkt(390, 395); //mu
    taschen.add(neuerPunkt);
    neuerPunkt = new Punkt(30, 390); //lu
    taschen.add(neuerPunkt);

    /*
    * liste der Eck koordinaten wird gefüllt
    * jeweils -10
    */
    neuerPunkt = new Punkt(30, 55); //0
    ecken.add(neuerPunkt);
    neuerPunkt = new Punkt(55, 30); //1
    ecken.add(neuerPunkt);
    neuerPunkt = new Punkt(360, 30); //2
    ecken.add(neuerPunkt);
    neuerPunkt = new Punkt(420, 30); //3
    ecken.add(neuerPunkt);
    neuerPunkt = new Punkt(725, 30); //4
    ecken.add(neuerPunkt);
    neuerPunkt = new Punkt(750, 55); //5
    ecken.add(neuerPunkt);
    neuerPunkt = new Punkt(750, 365); //6
    ecken.add(neuerPunkt);
    neuerPunkt = new Punkt(725, 390); //7
    ecken.add(neuerPunkt);
    neuerPunkt = new Punkt(420, 390); //8
    ecken.add(neuerPunkt);
    neuerPunkt = new Punkt(360, 390); //9
    ecken.add(neuerPunkt);
    neuerPunkt = new Punkt(55, 390); //10
    ecken.add(neuerPunkt);
    neuerPunkt = new Punkt(30, 365); //11
    ecken.add(neuerPunkt);
  }

  public static void setErstesEinlochen(boolean b) {
    erstesEinlochen = b;
  }

  public static boolean getErstesEinlochen() {
    return erstesEinlochen;
  }

  public static void setKugelEingelocht(boolean b) {
    kugelEingelocht = b;
  }

  public static boolean getKugelEingelocht() {
    return kugelEingelocht;
  }

  public static void setSollSpielerWechseln(boolean b) {
    sollSpielerWechseln = b;
  }

  public static void setKugelSetzen(boolean b) {
    kugelSetzen = b;
  }

  public static void setSollKugelSetzen(boolean b) {
    sollKugelSetzen = b;
  }

  public static void setSollFoulSetzen(boolean b) {
    sollFoulSetzen = b;
  }

  /*
  * setzt die kugeln auf die ausgangsposition zurück
  *
  * leert die kugelliste und füllt sie neu
  */
  public void neuStarten() {
    //SpielTisch neu erstellen
    this.setErstesEinlochen(true);
    s1.setKugelfarbe("");
    s2.setKugelfarbe("");
    foulanzeigeS1 = "";
    foulanzeigeS2 = "";

    kugelListe.clear();

    neueKugel = new Kugel(new Punkt(530, 210), new Punkt(0, 0)); //1 rote Kugeln
    kugelListe.add(neueKugel);
    neueKugel = new Kugel(new Punkt(570, 189), new Punkt(0, 0)); //4
    kugelListe.add(neueKugel);
    neueKugel = new Kugel(new Punkt(570, 231), new Punkt(0, 0)); //6
    kugelListe.add(neueKugel);
    neueKugel = new Kugel(new Punkt(590, 221), new Punkt(0, 0)); //7
    kugelListe.add(neueKugel);
    neueKugel = new Kugel(new Punkt(590, 199), new Punkt(0, 0)); //8 
    kugelListe.add(neueKugel); //       
    neueKugel = new Kugel(new Punkt(610, 210), new Punkt(0, 0)); //13 
    kugelListe.add(neueKugel); //          
    neueKugel = new Kugel(new Punkt(610, 252), new Punkt(0, 0)); //15    
    kugelListe.add(neueKugel); //         
    neueKugel = new Kugel(new Punkt(550, 199), new Punkt(0, 0)); //2 gelbe Kugeln  
    kugelListe.add(neueKugel); //              
    neueKugel = new Kugel(new Punkt(550, 221), new Punkt(0, 0)); //3           
    kugelListe.add(neueKugel);
    neueKugel = new Kugel(new Punkt(590, 178), new Punkt(0, 0)); //9
    kugelListe.add(neueKugel);
    neueKugel = new Kugel(new Punkt(590, 242), new Punkt(0, 0)); //10
    kugelListe.add(neueKugel);
    neueKugel = new Kugel(new Punkt(610, 168), new Punkt(0, 0)); //11
    kugelListe.add(neueKugel);
    neueKugel = new Kugel(new Punkt(610, 189), new Punkt(0, 0)); //12
    kugelListe.add(neueKugel);
    neueKugel = new Kugel(new Punkt(610, 231), new Punkt(0, 0)); //14
    kugelListe.add(neueKugel);
    neueKugel = new Kugel(new Punkt(209, 210), new Punkt(0, 0)); //15 weisse Kugel
    kugelListe.add(neueKugel);
    neueKugel = new Kugel(new Punkt(570, 210), new Punkt(0, 0)); //5 schwarze kugel
    kugelListe.add(neueKugel);
    derQueue = new Queue(); // der Queue
  }

  // KugelListe id weiterreichen
  public static ArrayList<Kugel> getKugelListe() {
    return kugelListe;
  }

  // Listener Methoden

  //MenüListener
  public void actionPerformed(ActionEvent ae) {
    // Funktionen der einzelnen Menüpunkte
    if (ae.getActionCommand().equals("Einzelspieler")) {
      /* ein training/testmodus ohne regeln, etc
      */
      neuStarten();

      anzeige2 = "Trainingsmodus";
      spielModus = "training";
      queueSichtbar = true;
      loift = true;
      this.startAnimation();
    }

    // Startet lokales Zweispieler Spiel
    if (ae.getActionCommand().equals("Zweispieler Lokal")) {
      neuStarten();

      String spielername1 = "";

      while ((spielername1.length() == 0) ||
               (spielername1.trim().length() > 12)) {
        spielername1 = JOptionPane.showInputDialog("Spieler 1, bitte geben Sie Ihren Namen ein:");

        if ((spielername1.trim().length() == 0) ||
              (spielername1.trim().length() > 12)) {
          JOptionPane.showMessageDialog(null,
                                        "Spielername muss zwischen 1 und 12 zeichen beinhalten.",
                                        "Achtung!", JOptionPane.PLAIN_MESSAGE);
        } // end if
      } // end while

      s1.setName(spielername1);

      String spielername2 = "";

      while ((spielername2.length() == 0) ||
               (spielername2.trim().length() > 12)) {
        spielername2 = JOptionPane.showInputDialog("Spieler 2, bitte geben Sie Ihren Namen ein:");

        if ((spielername2.trim().length() == 0) ||
              (spielername2.trim().length() > 12)) {
          JOptionPane.showMessageDialog(null,
                                        "Spielername muss zwischen 1 und 12 zeichen beinhalten.",
                                        "Achtung!", JOptionPane.PLAIN_MESSAGE);
        } // end if
      } // end while

      s2.setName(spielername2);
      spielModus = "zspl";

      loift = true;
      this.startAnimation();
    }

    if (ae.getActionCommand().equals("Netzwerkspiel starten")) {
      istServer = true;
      zeigeMeldung("Netzwerk starten");
      spielModus = "netz";
      this.queueSichtbar = true;

      s1.setStatus(true);
      s2.setStatus(false);
      System.out.println("starte Server");

      anzeige2 = "Warte auf Mitspieler...";
      repaint();

      //šffnet den server
      try {
        NetServer s = new NetServer();
      } catch (java.io.IOException e) {
        System.out.println("ganz bšser Fehler" + e);
      }

      System.out.println("Server offen");

      neuStarten();

      loift = true;
      this.startAnimation();

      //schickt namen an client
      try {
        s.schickeDaten(s.client, s1.getName());
      } catch (java.io.IOException e) {
        zeigeFehler("ein Verbindungsfehler ist aufgetreten. keine Verbindung.");
      }

      //empfŠngt namen vom client
      try {
        s.empfangeNamen(s.client);
        s2.setName(s.spieler2);
      } catch (java.io.IOException e) {
        zeigeFehler("ein Verbindungsfehler ist aufgetreten. keine Verbindung.");
      }
    }

    if (ae.getActionCommand().equals("Netzwerkspiel suchen")) {
      istServer = false;
      //zeigeMeldung("Netzwerk suchen");
      spielModus = "netz";
      this.queueSichtbar = true;
      s2.setStatus(true);
      s1.setStatus(false);

      System.out.println("verbinde..");

      NetClient c = new NetClient();

      //verbindet mit server
      while (!c.verbunden) {
        zeigeMeldung("Netzwerk suchen");

        try {
          c.connect(ip, s1.getName());
        } catch (java.io.IOException e) {
          //zeigeFehler("ein Verbindungsfehler ist aufgetreten. keine Verbindung.");
          System.out.println("Server nicht gefunden!");
        }
      }

      System.out.println("VERBUNDEN");
      neuStarten();

      loift = true;
      this.startAnimation();

      try {
        c.empfangeNamen(c.server);
        s2.setName(c.spieler2);
      } catch (java.io.IOException e) {
        zeigeFehler("ein Verbindungsfehler ist aufgetreten. keine Verbindung.");
      }

      try {
        c.schickeDaten(c.server, s1.getName());
      } catch (java.io.IOException e) {
        zeigeFehler("ein Verbindungsfehler ist aufgetreten. keine Verbindung.");
      }
    }

    if (ae.getActionCommand().equals("Statistik")) {
      zeigeStatistik("Statistik");
    }

    if (ae.getActionCommand().equals("Beenden")) {
      System.exit(0);
    }

    if (ae.getActionCommand().equals("Spielanleitung")) {
      String ueberschrift = "Spielanleitung";
      JLabel spielanleitung = new JLabel("<html> Willkommen zu unserem Spiel der Gruppe 3.4: Pool Billard<br><br>" +
                                         "Um ein Spiel zu starten wŠhlen Sie einen Spielmodus um Menüpunkt neues Spiel <br>" +
                                         "Sie kšnnen wŠhlen zwischen:<blockquote>- Einzelspieler</blockquote>" +
                                         "             <blockquote>- Zweispieler Lokal  </blockquote>" +
                                         "             <blockquote>- Netzwerkspiel (starten/suchen) </blockquote><br>" +
                                         "Bedienung: <br><br>" +
                                         "Benutzen Sie die Maus um den Queue um die wei§e Kugel zu bewegen.<br>" +
                                         "Wenn Sie den Winkel gefunden haben mit dem Sie die Kugel anspielen mšchten, <br>" +
                                         "drücken sie die linke Maustaste und halten sie diese gedrückt. <br>" +
                                         "In der unteren Menütafel auf der rechten Seite kšnnen Sie nun beobachten <br>" +
                                         "wie sich die Kraftanzeige hoch (rot - starker Sto§) und runter (grün - schwacher Sto§) bewegt. <br>" +
                                         "Lassen Sie die Maustaste bei gewünschter Kraft los und der Queue stš§t die Kugel.<br><br>" +
                                         "Einzelspieler: <br><br>" +
                                         "Spielen Sie einfach drauf los und machen Sie sich mit der Spielsteuerung vertraut. <br>" +
                                         "Ideal für eine kleine Pause zwischendurch.<br><br>" +
                                         "Zweispieler Lokal:  <br><br>" +
                                         "Suchen Sie sich einen Spielpartner und schauen Sie, wer der bessere Pool-Billard-Spieler ist. <br>" +
                                         "Diesen Modus spielt man an einem Computer, an welchem sich die Spieler abwechseln. <br>" +
                                         "Wer zuerst eine Kugel einer Farbe versenkt, bekommt diese Farbe zugeschrieben <br>" +
                                         "und es erscheint links neben dem Spielernamen in der unteren Menütafel ein Kreis in der jeweiligen Farbe. <br>" +
                                         "Die versenkten Kugeln werden rechts neben dem Namen in einer Reihe dargestellt. Gewonnen hat, <br>" +
                                         "wer zuerst alle Kugeln seiner Farbe und die Schwarze eingelocht hat. <br><br>" +
                                         "Netzwerkspiel (starten/suchen): <br><br>" +
                                         "Sie wollen dieses Spiel spielen, aber gern allein vor ihrem Computer sitzen und ihre Maus für sich allein haben, <br>" +
                                         "dann ist dieser Spielmodus genau das Richtige für Sie. <br>" +
                                         "Wenn Sie im Menüpunkt neues Spiel - Netzwerkspiel suchen/starten <br>" +
                                         "auswŠhlen šffnet sich ein neues Fenster.    <br>" +
                                         "Beim Netzwerkspiel Server müssen sie ihren Namen einfügen und starten. <br>" +
                                         "Beim Netzwerkspiel Client/suchen verbinden Sie sich mit Hilfe der IP-Addresse des Servers <br>" +
                                         "und geben ihren Namen ein. Los gehts! <br>" +
                                         "</html>");
      zeigeDialog(ueberschrift, spielanleitung);
    }

    if (ae.getActionCommand().equals("About...")) {
      //String ueberschrift = "About...";
      //JLabel about = new JLabel(
      //"<html><center>SoftwareProjekt: Pool Billard Gruppe 3.4 WS09/10<br>" +
      //"  Mitglieder: Basti, R. K., jette K., Hoffi Hoffmann, C. S., Batman und Robin<br>" +
      //"Special thanks to wikipedia and google.<center></html>");
      //zeigeDialog(ueberschrift, about);
      zeigeFehler("<html><center>SoftwareProjekt: Pool Billard Gruppe 3.4 WS09/10<br>" +
                  "  Mitglieder: Basti, R. K., jette K., Hoffi Hoffmann, C. S., Batman und Robin<br>" +
                  "Special thanks to wikipedia and google.<center></html>");
    }
  }

  //MouseMotionListener
  public void mouseMoved(MouseEvent me) {
    p1x = kugelListe.get(14).getKugelPos().x + 10;
    p1y = kugelListe.get(14).getKugelPos().y + 10; //P1 = Punkt der weissen Kugel
    p2x = me.getX();
    p2y = me.getY(); // P2 = Punkt des Mauszeigers
    MousePressedChecker = 0;

    schwungfaktorX = 0;
    schwungfaktorY = 0;

    //liefert den Winkel zwischen Punkt weisser Kugel zu Mauspunkt
    awinkel = derQueue.getMausWinkel(p1x, p1y, p2x, p2y);

    //anzeige = "x= "+me.getX()+"  y= "+me.getY()+" Winkel= "+awinkel+" SchwungfaktorX= "+schwungfaktorX;
    repaint();
  }

  public void mouseDragged(MouseEvent me) {
  }

  //MouseListener
  public void mousePressed(MouseEvent me) {
    p1x = kugelListe.get(14).getKugelPos().x + 10;
    p1y = kugelListe.get(14).getKugelPos().y + 10; //P1 = Punkt der weissen Kugel
    p2x = me.getX();
    p2y = me.getY(); // P2 = Punkt des Mauszeigers

    MousePressedChecker = 1;
    stBegin = System.currentTimeMillis();
  }

  public void mouseReleased(MouseEvent e) {
    kugelAngespielt = false; //reset
    setKugelEingelocht(false);

    schwung = 0.2;
    MousePressedChecker = 0;
    schwungfaktorX = 0;
    schwungfaktorY = 0;

    /*
    * aus dem zeitunterschied, ab wann die maus gedrückt wurde und
    * wann losgelassen, wird hier die sto§kraft ermittelt
    */
    stEnde = System.currentTimeMillis();
    stDelta = Math.abs(stBegin - stEnde);
    stKraft = ((stDelta / 3) % 1000) / 50;

    //wenn kugel gesetz werden soll, wird nur die kugel abgesetz
    //und nur wenn nicht, wird ein sto§ durchgeführt
    if ((!kugelSetzen) && (this.queueSichtbar)) {
      if (stKraft > 10) {
        stKraft = 20 - stKraft;
      }

      double abstandx = (p2x - 10 - kugelListe.get(14).getKugelPos().x);
      double abstandy = (p2y - 10 - kugelListe.get(14).getKugelPos().y);

      double normrichtungx = abstandx / Math.sqrt((abstandx * abstandx) +
                                                  (abstandy * abstandy));
      double normrichtungy = abstandy / Math.sqrt((abstandx * abstandx) +
                                                  (abstandy * abstandy));
      kugelListe.get(14)
                .setKugelVektor(new Punkt(Math.round(-1.9 * stKraft * normrichtungx * 100) / 100.,
                                          Math.round(-1.9 * stKraft * normrichtungy * 100) / 100.0));

      if (!((c.gesendet) || (s.gesendet))) {
        if ((spielModus == "netz") && (s1.getStatus())) {
          if (istServer == true) {
            try {
              s.schickeDaten(s.client,
                             kugelListe.get(14).getKugelVektor().toString());
              s.gesendet = true;
            } catch (java.io.IOException ie) {
              zeigeFehler("ein Verbindungsfehler ist aufgetreten. keine Verbindung.");
            }
          } else {
            try {
              c.schickeDaten(c.server,
                             kugelListe.get(14).getKugelVektor().toString() +
                             " 0");
              c.gesendet = true;
            } catch (java.io.IOException ie) {
              zeigeFehler("ein Verbindungsfehler ist aufgetreten. keine Verbindung.");
            }
          }
        }
      }

      if (s1.getStatus()) {
        s1.setAnzahlStoesse();
      }

      if (s2.getStatus()) {
        s2.setAnzahlStoesse();
      }

      gestossen = true;
      stKraft = 0;
    }

    while (((kugelSetzen) &&
             ((spielModus == "training") || (spielModus == "zspl") ||
               ((spielModus == "netz") && (s1.getStatus()))))) {
      if ((e.getX() >= 50) && (e.getX() <= 220) && (e.getY() >= 50) &&
            (e.getY() <= 390)) {
        kugelListe.get(14).setKugelPos(new Punkt(e.getX() - 10, e.getY() - 10));
        setKugelSetzen(false);

        for (int i = 0; i < kugelListe.size(); i++) {
          if ((p.abstandBerechnung(kugelListe.get(14).getKugelPos(),
                                     kugelListe.get(i).getKugelPos()) <= 20) &&
                (i != 14)) {
            JOptionPane.showMessageDialog(null, "Neusetzen",
                                          "Bitte die weisse Kugel an einen anderen Ort setzen",
                                          JOptionPane.ERROR_MESSAGE);
            setSollKugelSetzen(true);
            kugelListe.get(14).setKugelPos(new Punkt(667, 6665));
            repaint();
          }
        }

        if ((spielModus == "netz") && (!sollKugelSetzen)) {
          //sende position
          try {
            if (istServer == true) {
              s.schickeDaten(s.client,
                             kugelListe.get(14).getKugelPos().toString());
            } else {
              c.schickeDaten(c.server,
                             kugelListe.get(14).getKugelPos().toString());
            }
          } catch (java.io.IOException ex) {
            zeigeFehler("ein Verbindungsfehler ist aufgetreten. keine Verbindung.");
          }
        }
      }
    }
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
    MousePressedChecker = 0;
    schwungfaktorX = 0;
    schwungfaktorY = 0;
  }

  // Ereignisprozeduren
  public void startAnimation() {
    new Thread(this).start();
  }

  //bewegt eine kugel
  public void bewege(Kugel k) {
    Punkt altpos = k.getKugelPos();
    Punkt richtungsvektor = k.getKugelVektor();

    k.setKugelPos(new Punkt(altpos.x + richtungsvektor.x,
                            altpos.y + richtungsvektor.y));

    //abbremsen der kugel auf dem tisch
    k.setKugelVektor(new Punkt(k.getKugelVektor().x * p.bremsfaktorTisch,
                               k.getKugelVektor().y * p.bremsfaktorTisch));
  }

  // abwechselndes spieln an einem pc, bisher ohne regeln.
  public void hotSeat() {
    if (kugelnBewegenSich == true) {
      queueSichtbar = false;
      //anzeige2 = "Bitte warten...";
      stKraft = 0; //anstoss nicht mehr moeglich
    }

    if ((s1.getStatus() == true) && (kugelnBewegenSich == false)) {
      queueSichtbar = true;
      anzeige2 = (s1.getName() + " ist dran.");
    } else if ((s1.getStatus() == false) && (kugelnBewegenSich == false)) {
      queueSichtbar = true;
      anzeige2 = (s2.getName() + " ist dran.");
    }

    if (kugelSetzen) {
    
      if (s1.getStatus()) {
        anzeige2 = s1.getName() +
                   ", platzieren Sie bitte die Kugel im Kopffeld";
      }

      if (s2.getStatus()) {
        anzeige2 = s2.getName() +
                   ", platzieren Sie bitte die Kugel im Kopffeld";
      }
    }
  }

  public void netzSpiel() {
    if (kugelnBewegenSich == true) {
      queueSichtbar = false;
      //anzeige2 = "Bitte warten...";
      stKraft = 0; //anstoss nicht mehr moeglich
    }

    if ((s1.getStatus() == true) && (kugelnBewegenSich == false)) {
      queueSichtbar = true;
      anzeige2 = (s1.getName() + " ist dran.");
    } else if ((s1.getStatus() == false) && (kugelnBewegenSich == false)) {
      queueSichtbar = false;
      anzeige2 = (s2.getName() + " ist dran.");

      if ((!((c.erhalten) || (s.erhalten))) && (!posEmpfangen)) {
        try {
          if (istServer == true) {
            s.empfangeDaten(s.client);
            kugelListe.get(14).setKugelVektor(new Punkt(s.xVek, s.yVek));
            kugelAngespielt = false; //reset
            setKugelEingelocht(false);
            gestossen = true;
          } else {
            c.empfangeDaten(c.server);
            kugelListe.get(14).setKugelVektor(new Punkt(c.xVek, c.yVek));
            kugelAngespielt = false; //reset
            setKugelEingelocht(false);
            gestossen = true;
          }
        } catch (java.io.IOException e) {
          zeigeFehler("Verbindung wurde beendet.");
        }

        s2.setAnzahlStoesse();
      }
    }

    if (kugelSetzen) {
      if (s1.getStatus()) {
        anzeige2 = s1.getName() + ", platzieren bitte die Kugel im Kopffeld";
      }
    }

    if (posEmpfangen) {
      posEmpfangen = false;

      if (istServer == true) {
        try {
          s.empfangeWeissePos(s.client);
          kugelListe.get(14).setKugelPos(new Punkt(s.xPos, s.yPos));
          repaint();
        } catch (java.io.IOException e) {
          System.out.println("empfange kugelposition kaputt" + e);
        }
      } else {
        try {
          c.empfangeWeissePos(c.server);
          kugelListe.get(14).setKugelPos(new Punkt(c.xPos, c.yPos));
          repaint();
          setKugelSetzen(false);
        } catch (java.io.IOException e) {
          System.out.println("empfange kugel kaputt" + e);
        }
      }
    }
  }

  //alles einlochen, wurde vom Auftrageber gewuenscht das er immer gewinnt!
  public void allesEinlochen() {
    if ((s1.getName().equals("mathias")) || (s1.getName().equals("Mathias"))) {
      s1.setKugelfarbe("rot");
      p.eingelocht(0);
      p.eingelocht(1);
      p.eingelocht(2);
      p.eingelocht(3);
      p.eingelocht(4);
      p.eingelocht(5);
      p.eingelocht(6);

      kugelListe.get(7).setKugelPos(new Punkt(315, 60));
      kugelListe.get(8).setKugelPos(new Punkt(385, 60));

      kugelListe.get(9).setKugelPos(new Punkt(310, 110));
      kugelListe.get(10).setKugelPos(new Punkt(330, 120));
      kugelListe.get(11).setKugelPos(new Punkt(350, 125));
      kugelListe.get(12).setKugelPos(new Punkt(370, 120));
      kugelListe.get(13).setKugelPos(new Punkt(390, 110));

      kugelListe.get(15).setKugelPos(new Punkt(55, 55));
      kugelListe.get(14).setKugelPos(new Punkt(80, 80));
      s1.resetFoul();
      s2.resetFoul();
      repaint();
    }

    if (((spielModus == "netz") && s2.getName().equals("mathias")) ||
          (s1.getName().equals("Mathias"))) {
      p.eingelocht(0);
      p.eingelocht(1);
      p.eingelocht(2);
      p.eingelocht(3);
      p.eingelocht(4);
      p.eingelocht(5);
      p.eingelocht(6);
      s1.resetFoul();
      s2.resetFoul();
      kugelListe.get(7).setKugelPos(new Punkt(315, 60));
      kugelListe.get(8).setKugelPos(new Punkt(385, 60));

      kugelListe.get(9).setKugelPos(new Punkt(310, 110));
      kugelListe.get(10).setKugelPos(new Punkt(330, 120));
      kugelListe.get(11).setKugelPos(new Punkt(350, 125));
      kugelListe.get(12).setKugelPos(new Punkt(370, 120));
      kugelListe.get(13).setKugelPos(new Punkt(390, 110));

      kugelListe.get(15).setKugelPos(new Punkt(55, 55));
      kugelListe.get(14).setKugelPos(new Punkt(80, 80));
      s1.setKugelfarbe("gelb");

      //this.sollFoulSetzen = false;
      repaint();
    }

    if ((s1.getName().equals("mathias")) || (s1.getName().equals("Mathias"))) {
      s1.setName("Auftraggeber");
    }
  }

  /*
  * Hier werden die Kugelkoordinaten bewegt und mit hilfe der Physik geprüft.
  */
  public void bewegungsManager() {
    if (MousePressedChecker >= 1) {
      schwungfaktorX = derQueue.schwungfaktorX(p1x, p1y, p2x, p2y,
                                               MousePressedChecker,
                                               schwungfaktorX, schwung);
      schwungfaktorY = derQueue.schwungfaktorY(p1x, p1y, p2x, p2y,
                                               MousePressedChecker,
                                               schwungfaktorY, schwung);
    }

    if (MousePressedChecker == 1) {
      /*
      * aus dem zeitunterschied, ab wann die maus gedrückt wurde und
      * der aktuellen systemzeit, wird hier die sto§kraft für die
      * dynamische anzeige ermittelt
      */
      if (stKraft == 0) {
        schwung = 0.2;
      }

      if (stKraft == 10) {
        schwung = -0.2;
      }

      stDelta = System.currentTimeMillis() - stBegin;
      stKraft = ((stDelta / 3) % 1000) / 50;

      if (stKraft > 10) {
        stKraft = 20 - stKraft;
      }
    }

    /* prüft ob spieler im letzten zug gefoult hat und setz entsprechend die werte
    */
    if (!s1.getGefoult()) {
      s1.resetFoulHinereinander();
    }

    if (!s2.getGefoult()) {
      s2.resetFoulHinereinander();
    }

    Punkt gesammtV = new Punkt(0, 0); //gesammt geschwindigleit wird reseted

    //    berechneDelta();// für die fps anzeige (kann weg, wenn fps weg)

    /*
    * jede Kugel wird zuerst mit jeder anderen Kugel auf Kugelkollision geprüft.
    * danach wird sie auf kollison mit den banden geprüft und überprüft, ob die kugel sich
    * im taschenbereich aufhŠlt ( wo dann auf einlochen und kollision mit innenwŠnden geprüft wird)
    * zum schluss wird die kugel um den richtungsvektor bewegt
    */
    for (int i = 0; i < getKugelListe().size(); i++) {
      Punkt pos = kugelListe.get(i).getKugelPos();

      gesammtV = new Punkt(gesammtV.x + kugelListe.get(i).getKugelVektor().x,
                           gesammtV.y + kugelListe.get(i).getKugelVektor().y);

      for (int j = i + 1; j < getKugelListe().size(); j++) {
        p.pruefeKollisionKugeln(i, j);
      } //for j

      p.pruefeKollisionBande(i);

      for (int k = 0; k < 6; k++) {
        p.pruefeTasche(i, k);
      }

      bewege(kugelListe.get(i));

      p.kugelAnhalten(i);
    } //for i

    /*
    * prüfe die gesammtgeschwindigkeit und setze bei 0 den wert
    * kugelnBewegenSich auf false
    */
    if ((gesammtV.x == 0) && (gesammtV.y == 0)) {
      if ((gestossen == true) && (kugelAngespielt == false)) {
        setSollFoulSetzen(true);

        if (s1.getFoulHintereinander() >= 3) {
          Regelwerk.spielZuEnde("s2"); //wenn foulanzahl = 3 hat gegner gewonnen
        }

        if (s2.getFoulHintereinander() >= 3) {
          Regelwerk.spielZuEnde("s1");
        }

        sollSpielerWechseln = true;
      }

      if ((gestossen == true) && (kugelEingelocht == false) &&
            (kugelAngespielt == true)) {
        sollSpielerWechseln = true;
      }

      //erst wenn alle kugeln stehn, wird bei bedarf der spieler gewechselt
      if ((sollFoulSetzen) && (gestossen == true)) {
        if (s1.getStatus()) {
          s1.setFoul();
          s1.setFoulHintereinander();
          s1.setGefoult(true);
        }

        if (s2.getStatus()) {
          s2.setFoul();
          s2.setFoulHintereinander();
          s2.setGefoult(true);
        }

        setSollFoulSetzen(false);

        if (SpielTisch.s1.getFoulHintereinander() >= 3) {
          Regelwerk.spielZuEnde("s2"); //wenn foulanzahl = 3 hat gegner gewonnen
        }

        if (SpielTisch.s2.getFoulHintereinander() >= 3) {
          Regelwerk.spielZuEnde("s1");
        }
      } else if (gestossen) {
        if (s1.getStatus()) {
          s1.setGefoult(false);
        }

        if (s2.getStatus()) {
          s2.setGefoult(false);
        }
      }

      gestossen = false; //reset

      if (sollSpielerWechseln) {
        Regelwerk.spielerWechseln();
      }

      if (sollKugelSetzen) {
        if ((spielModus == "zspl") || (spielModus == "training") ||
              ((spielModus == "netz") && (s1.getStatus()))) {
          kugelSetzen = true;
        }

        if ((spielModus == "netz") && (s2.getStatus())) {
          posEmpfangen = true;
        }

        sollKugelSetzen = false;
      }

      //resetet die boolean dinger
      if (istServer == true) {
        s.erhalten = false;
        s.gesendet = false;
      } else {
        c.erhalten = false;
        c.gesendet = false;
      }

      kugelnBewegenSich = false;
    } else {
      kugelnBewegenSich = true;
    }

    repaint();

    try {
      Thread.sleep((long) (15));
    } catch (InterruptedException e) {
      System.out.println("Fehler!" + e);
    }
  } // bewegungsManager()

  // fuehrt das spiel aus
  public void run() {
    //blockiert wŠhrend des Spiels das ein neues Spiel gestartet werden kann
    if ((spielModus.equals("training")) || (spielModus.equals("zspl")) ||
          (spielModus.equals("netz"))) {
      submenu.setEnabled(false);

      //blockiert -> menuItem1,menuItem2, host, client, menuItem4
    }

    while ((spielModus == "zspl") && (loift)) {
      bewegungsManager();
      hotSeat();
      allesEinlochen();

      //this.kugelListe.get(15).setKugelVektor(new Punkt(-1,-1)); // schwarze kugel einlochen
      //this.bewege(this.kugelListe.get(15));
    } //while(true)

    while ((spielModus == "training") && (loift)) {
      bewegungsManager();
      allesEinlochen();

      if (kugelnBewegenSich == true) {
        queueSichtbar = false;
        //anzeige2 = "Bitte warten...";
        stKraft = 0; //anstoss nicht mehr moeglich
      } else {
        queueSichtbar = true;
      }

      //resete alle spieler/regelwerte STÄNDIG
      s1.resetFoul();
      s2.resetFoul();
      s1.resetFoulHinereinander();
      s2.resetFoulHinereinander();
      s1.setKugelfarbe("");
      s2.setKugelfarbe("");
      s1.setStatus(true);
      s2.setStatus(false);
    } //while(true)

    while ((spielModus == "netz") && (loift)) {
      bewegungsManager();
      netzSpiel();
      allesEinlochen();
    } // while
  } //run

  //zerstoert das Fenster, alle MouseListener und die Ober§aeche
  public void zerstoere() {
    this.getGlassPane().removeMouseListener(this);
    this.getGlassPane().removeMouseMotionListener(this);
    this.getContentPane().removeAll();
    this.getLayeredPane().removeAll();
  }

  // innere Klasse ImagePanel ueberlaedt das JPanel
  class ImagePanel extends JPanel {
    // Anfang Attribute1
    // Ende Attribute1
    ImagePanel(String[] images) {
      imgs = new Image[images.length];

      MediaTracker mt = new MediaTracker(this);

      for (int i = 0; i < images.length; i++) {
        imgs[i] = Toolkit.getDefaultToolkit().getImage(images[i]);
        mt.addImage(imgs[i], i);
      }

      try {
        mt.waitForAll();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      // Anfang Komponenten1
      // Ende Komponenten1
    }

    // Anfang Methoden1
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(imgs[0], 0, 0, null); //spieltisch

      //zeichnen des kopffeldes
      if (kugelSetzen) {
        g.setColor(Color.white);
        g.drawLine(40, 40, 220, 40);
        g.drawLine(220, 40, 220, 400);
        g.drawLine(220, 400, 40, 400);
        g.drawLine(40, 400, 40, 40);
      }

      /*
      * einstellung der schriftart
      *
      * importiert einen externen font und setzt die schriftgrš§e auf 25
      */
      try {
        Font f1 = Font.createFont(Font.TRUETYPE_FONT,
                                  new File("font/erasdust.ttf"));
        kreide = f1.deriveFont(25f);
      } catch (Exception e) {
        System.out.println(e);
      }

      /*
      * ertellt aus dem bestehenden font kreide einen zweiten
      * mit der Schritgrš§e 20
      */
      Font kreide2 = kreide.deriveFont(20f);

      /*
      * spielinformationen werden auf der tafel gezeichnet
      */
      g.setFont(kreide);
      g.setColor(Color.WHITE);
      g.drawString(anzeige2, 40, 495);

      /*
      * je nachdem wie die ermittelte sto§kraft ist,
      * wird das entsprechende bild (von 10) gezeichnet.
      */
      if (loift) {
        g.drawString("Kraft", 480, 545);
      }

      if (loift) {
        g.drawImage(imgs[(7 + (int) stKraft)], 550, 510, null);
      }

      /*
      * beide Spielernamen werden hier auf der tafel gezeichnet
      */
      g.setFont(kreide2);
      g.drawString(s1.getName(), 90, 535);
      g.drawString(s2.getName(), 90, 560);

      g.setColor(Color.GRAY); // grau, da keine farben vergeben

      if (s1.getKugelfarbe() == "rot") { //farbe von s1 wird gezeichnet
        g.setColor(Color.red);
        g.drawString("o", 70, 535);
      }

      if (s1.getKugelfarbe() == "gelb") { //farbe von s1 wird gezeichnet
        g.setColor(Color.yellow);
        g.drawString("o", 70, 535);
      }

      if (s2.getKugelfarbe() == "rot") { //farbe von s2 wird gezeichnet
        g.setColor(Color.red);
        g.drawString("o", 70, 560);
      }

      if (s2.getKugelfarbe() == "gelb") { //farbe von s2 wird gezeichnet
        g.setColor(Color.yellow);
        g.drawString("o", 70, 560);
      }

      g.setColor(Color.white);

      switch (s1.getFoulHintereinander()) {
        case (0):
          break;

        case (1):
          g.drawString("X", 30, 535);

          break;

        case (2):
          g.drawString("XX", 30, 535);

          break;

        case (3):
          g.drawString("XXX", 30, 535);

          break;
      }

      switch (s2.getFoulHintereinander()) {
        case (0):
          break;

        case (1):
          g.drawString("X", 30, 560);

          break;

        case (2):
          g.drawString("XX", 30, 560);

          break;

        case (3):
          g.drawString("XXX", 30, 560);

          break;
      }

      g.drawImage(imgs[1], (int) kugelListe.get(0).getKugelPos().x,
                  (int) kugelListe.get(0).getKugelPos().y, null); //rote Kugeln
      g.drawImage(imgs[1], (int) kugelListe.get(1).getKugelPos().x,
                  (int) kugelListe.get(1).getKugelPos().y, null);
      g.drawImage(imgs[1], (int) kugelListe.get(2).getKugelPos().x,
                  (int) kugelListe.get(2).getKugelPos().y, null);
      g.drawImage(imgs[1], (int) kugelListe.get(3).getKugelPos().x,
                  (int) kugelListe.get(3).getKugelPos().y, null);
      g.drawImage(imgs[1], (int) kugelListe.get(4).getKugelPos().x,
                  (int) kugelListe.get(4).getKugelPos().y, null);
      g.drawImage(imgs[1], (int) kugelListe.get(5).getKugelPos().x,
                  (int) kugelListe.get(5).getKugelPos().y, null);
      g.drawImage(imgs[1], (int) kugelListe.get(6).getKugelPos().x,
                  (int) kugelListe.get(6).getKugelPos().y, null);
      g.drawImage(imgs[2], (int) kugelListe.get(7).getKugelPos().x,
                  (int) kugelListe.get(7).getKugelPos().y, null);
      g.drawImage(imgs[2], (int) kugelListe.get(8).getKugelPos().x,
                  (int) kugelListe.get(8).getKugelPos().y, null); //gelbe Kugeln
      g.drawImage(imgs[2], (int) kugelListe.get(9).getKugelPos().x,
                  (int) kugelListe.get(9).getKugelPos().y, null);
      g.drawImage(imgs[2], (int) kugelListe.get(10).getKugelPos().x,
                  (int) kugelListe.get(10).getKugelPos().y, null);
      g.drawImage(imgs[2], (int) kugelListe.get(11).getKugelPos().x,
                  (int) kugelListe.get(11).getKugelPos().y, null);
      g.drawImage(imgs[2], (int) kugelListe.get(12).getKugelPos().x,
                  (int) kugelListe.get(12).getKugelPos().y, null);
      g.drawImage(imgs[2], (int) kugelListe.get(13).getKugelPos().x,
                  (int) kugelListe.get(13).getKugelPos().y, null);
      g.drawImage(imgs[3], (int) kugelListe.get(14).getKugelPos().x,
                  (int) kugelListe.get(14).getKugelPos().y, null); //Weisse Kugel
      g.drawImage(imgs[4], (int) kugelListe.get(15).getKugelPos().x,
                  (int) kugelListe.get(15).getKugelPos().y, null); //schwarze Kugel
                                                                   //falls kugel gesetz werden soll, wird sie dort gezeichnet, wo die maus ist

      if (kugelSetzen) {
        g.drawImage(imgs[3], (int) p2x - 10, (int) p2y - 10, null);
      }

      if (queueSichtbar) {
        if (MousePressedChecker == 0) {
          derQueue.bewegenderQueue(g, imgs[5], awinkel,
                                   kugelListe.get(14).getKugelPos().x,
                                   kugelListe.get(14).getKugelPos().y); // zeichnet Queue mit Drehung
        }

        if (MousePressedChecker >= 1) {
          derQueue.bewegenderQueue(g, imgs[5], awinkel,
                                   (int) kugelListe.get(14).getKugelPos().x +
                                   (schwungfaktorX * 20),
                                   (int) kugelListe.get(14).getKugelPos().y +
                                   (schwungfaktorY * 20));
        }

        try {
          Thread.sleep((long) (20));
        } // damit der queue noch für ein paar millisekunden an der kugel klebt, bevor er unsichtbar wird
        catch (InterruptedException e) {
          System.out.println("Fehler!" + e);
        } // auf die schnelle ist mir nichts besseres eingefallen
      }
    }

    // Ende Methoden1
  }

  private void zeigeMeldung(String meldung) {
    final JDialog meldungDialog = new JDialog();
    final JTextField tf1 = new JTextField("Bitte Namen eingeben");
    final JTextField tf2 = new JTextField();
    final JTextField tf3 = new JTextField("1764");
    meldungDialog.setLayout(new GridLayout(4, 2));
    meldungDialog.add(new JLabel("   Name "));
    meldungDialog.add(tf1);
    tf3.setEditable(false);

    if (this.istServer) {
      meldungDialog.add(new JLabel("  meine IP ist "));
      tf2.setEditable(false);
    } else {
      meldungDialog.add(new JLabel("  verbinde mit IP "));
    }

    meldungDialog.add(tf2);
    meldungDialog.add(new JLabel("   Port "));
    meldungDialog.add(tf3);
    meldungDialog.setTitle(meldung);
    meldungDialog.setModal(true);
    meldungDialog.setLocation(100, 100);

    if (this.istServer) {
      tf2.setText(s.getMeineIP());
    } else {
      tf2.setText("127.0.0.1");
    }

    JButton ok = new JButton("OK");
    ok.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (tf1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Bitte Namen eingeben",
                                          "Bitte Namen eingeben",
                                          JOptionPane.ERROR_MESSAGE);
          } else {
            s1.setName(tf1.getText());

            if (tf2.getText().equals("")) {
              JOptionPane.showMessageDialog(null, "Bitte IP-Addresse eingeben",
                                            "Bitte IP-Addresse eingeben",
                                            JOptionPane.ERROR_MESSAGE);
            } else {
              ip = tf2.getText();
              System.out.println("_____Bitte Warten_____");
              meldungDialog.dispose();
            }
          }
        }
      });

    JButton abbrechen = new JButton("Abbrechen");
    abbrechen.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.exit(0);
        }
      });
    meldungDialog.add(ok);
    meldungDialog.add(abbrechen);
    meldungDialog.setSize(350, 200);
    meldungDialog.setVisible(true);
    meldungDialog.validate();
    meldungDialog.pack();
  }

  public static void zeigeStatistik(String statistik) {
    final JDialog meldungDialog = new JDialog();

    JButton ok = new JButton("OK");
    ok.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          meldungDialog.dispose();

          if (regelwerkvar == "") {
            meldungDialog.dispose(); // Solange keiner gewonnen hat schlie§t sich der Dialog einfach bei "ok"
          } else { // sobald feststeht wer gewonnen hat, ist das Spiel zu Ende und das Programm wird beendet

            JDialog endeDialog = new JDialog();
            JLabel danke = new JLabel("Vielen Dank fürs Spielen!");
            endeDialog.setLayout(new GridLayout(2, 1));

            JButton ok = new JButton("OK");
            ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                  System.exit(0);
                }
              });
            endeDialog.setModal(true);
            endeDialog.add(danke);
            endeDialog.add(ok);
            endeDialog.pack();
            endeDialog.validate();
            endeDialog.setLocation(650, 400);
            endeDialog.setVisible(true);
          }

          ;
        }
      });

    JLabel ausgabe = new JLabel(regelwerkvar);

    JPanel inner = new JPanel();
    inner.setLayout(new BorderLayout());

    meldungDialog.setLayout(new BorderLayout());

    // Die Daten für das Table
    String a = Integer.toString((s1.getFoul()));
    String c = Integer.toString((s2.getFoul()));
    String q = s1.getName();
    String w = s2.getName();
    String f = Integer.toString(7 - s1.getAnzahlEingelocht());
    String g = Integer.toString(7 - s2.getAnzahlEingelocht());
    String j = Integer.toString(s1.getAnzahlStoesse());
    String k = Integer.toString(s2.getAnzahlStoesse());

    //gewinnrunden
    String[][] data = new String[][] {
                        { "Spieler", q, w },
                        { "Begangene Fouls", a, c },
                        { "Anzahl der Stš§e", j, k },
                        { "Verbleibende Kugeln", f, g }
                      };

    // Die Column-Titles
    String[] title = new String[] { "Attribute", "Spieler1", "Spieler2", };
    JTable stats = new JTable(data, title);
    stats.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    meldungDialog.setLocation(100, 100);
    meldungDialog.add(new JLabel(statistik), BorderLayout.NORTH);
    meldungDialog.add(inner, BorderLayout.CENTER);
    inner.add(stats, BorderLayout.NORTH);
    inner.add(ausgabe, BorderLayout.CENTER);
    meldungDialog.add(ok, BorderLayout.SOUTH);
    meldungDialog.setModal(true);
    meldungDialog.setSize(500, 200);
    meldungDialog.setVisible(true);
    meldungDialog.validate();
    meldungDialog.pack();
  }

  private void zeigeDialog(String head, final JLabel dialog) {
    final JDialog meldungDialog = new JDialog();

    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BorderLayout());
    getContentPane().add(topPanel);

    JLabel label = dialog;

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.getViewport().add(label);
    scrollPane.setAutoscrolls(true);
    scrollPane.setWheelScrollingEnabled(true);

    topPanel.add(scrollPane, BorderLayout.CENTER);

    meldungDialog.setSize(750, 500);
    meldungDialog.setLayout(new BorderLayout());

    JButton ok = new JButton("OK");
    ok.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          meldungDialog.dispose();
        }
      });

    topPanel.add(ok, BorderLayout.SOUTH);

    meldungDialog.setLocation(140, 100);
    meldungDialog.add(topPanel);
    meldungDialog.setModal(true);
    meldungDialog.setTitle(head);
    meldungDialog.setVisible(true);
    meldungDialog.validate();
    meldungDialog.pack();
  }

  public void zeigeFehler(String s) {
    JDialog meldungDialog = new JDialog();
    meldungDialog.setLayout(new BorderLayout());

    JLabel ausgabe = new JLabel(s);
    JButton ok = new JButton("OK");
    ok.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.exit(1);
        }
      });
    meldungDialog.add(ausgabe, BorderLayout.CENTER);
    meldungDialog.add(ok, BorderLayout.SOUTH);
    meldungDialog.setSize(350, 200);
    meldungDialog.setLocation(100, 100);
    meldungDialog.setVisible(true);
    meldungDialog.validate();
    meldungDialog.pack();
  }

  public static void main(String[] args) {
    SpielTisch tisch = new SpielTisch("SpielTisch");
    tisch.startAnimation();
  }

  // Ende Methoden
}
