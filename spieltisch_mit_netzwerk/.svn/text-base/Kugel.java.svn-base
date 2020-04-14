import java.awt.Image;
import java.awt.Point;

import java.io.Serializable;


public class Kugel implements Serializable {
  private double x = 0;
  private double y = 0;
  Punkt p = new Punkt(x, y);
  private Punkt koordinaten;
  private Punkt vektor;

  public final boolean bewegtsich() {
    return (!((getKugelVektor().x == 0) && (getKugelVektor().y == 0)));
  } // bewegtsich

  // erstellt eine Kugel,
  // Kugel benötigt Koordinaten/Position und einen Vektor (x, y)
  public Kugel(int posX, int posY, Punkt richtungsVektor) {
    koordinaten.x = posX;
    koordinaten.y = posY;
    vektor = richtungsVektor;
  }

  // erstellt eine Kugel,
  // Kugel benï¿½tigt Koordinaten/Position und einen Vektor (x, y)
  public Kugel(Punkt koordi, Punkt richtungsVektor) {
    koordinaten = koordi;
    vektor = richtungsVektor;
  }

  // gibt den Punkt der Kugel zurï¿½ck
  public Punkt getKugelPos() {
    return koordinaten;
  }

  // legt die neue Position der Kugel fest
  public void setKugelPos(Punkt p) {
    koordinaten = p;
  }

  // gibt den Vektor der Kugel zurï¿½ck
  public Punkt getKugelVektor() {
    return vektor;
  }

  // legt einen neuen Vektor fest
  public void setKugelVektor(Punkt vek) {
    vektor = vek;
  }
}
