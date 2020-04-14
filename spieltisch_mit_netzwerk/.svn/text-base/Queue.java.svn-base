import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;


public class Queue extends Frame {
  public static void main(String[] args) {
    new Queue();
  }

  public double getMausWinkel(double p1x, double p1y, double p2x, double p2y) {
    /*
    Diese Methode dient zur Berrechnung des Winkels, der
    entsteht, wenn der Mausszeiger um die weiße Kugel bewegt wird.
    Durch die Koordinaten des Mittelpunktes P1 der weißen Kugel (p1x,p1y) und
    den Koordinaten der Mausposition P2 (p2x,p2y), wird mithilfe der Winkelfunktion
    Tangens der Winkel berrechnet.
    Dazu dienen die 4.Bedingugnen für jeweils 4 Berreiche um die weiße Kugel.
    Da der Tangens keine vollständige 90° berrechnen kann werden noch 3. Bedingungen
    benötigt, um die vollen Winkel anzugeben.
    */
    double awinkel = 0;

    if ((p1y == p2y) && (p2x > p1x)) { //Wenn 0° erreicht sind
      awinkel = 0;
    }

    if ((p2x > p1x) && (p2y > p1y)) { //0°-90°

      double winkel = ((p2y - p1y) / (p2x - p1x));
      awinkel = Math.toDegrees(Math.atan(winkel));
    }

    if ((p1x == p2x) && (p2y > p1y)) { //Wenn 90° erreicht sind
      awinkel = 90;
    }

    if ((p2x < p1x) && (p2y > p1y)) { //90°-180°

      double winkel = ((p1x - p2x) / (p2y - p1y));
      awinkel = Math.toDegrees(Math.atan(winkel)) + 90;
    }

    if ((p1y == p2y) && (p2x < p1x)) { //Wenn 180° erreicht sind
      awinkel = 180;
    }

    if ((p2x < p1x) && (p2y < p1y)) { //180°-270°

      double winkel = ((p2y - p1y) / (p2x - p1x));
      awinkel = Math.toDegrees(Math.atan(winkel)) + 180;
    }

    if ((p1x == p2x) && (p2y < p1y)) { // Wenn 270° erreicht sind
      awinkel = 270;
    }

    if ((p2x > p1x) && (p2y < p1y)) { //270°-0°

      double winkel = ((p2x - p1x) / (p1y - p2y));
      awinkel = Math.toDegrees(Math.atan(winkel)) + 270;
    }

    return awinkel;
  } //getMausWinkel

  public double schwungfaktorX(double p1x, double p1y, double p2x, double p2y,
                               double MousePressedChecker,
                               double schwungfaktorX, double schwung) {
    /*
    In dieser Methode wird der SchwungfaktorX bestimmt. Ein Zahlen Wert der zurück gibt,
    in welche X-Richtung sich der Queue beim Stoß bewegen soll.
    Dazu dienen die 8.Bedingugnen für jeweils 8 Berreiche um die weiße Kugel,
    damit der Queue gleichmässig zurück gezogen wird.
    */
    double winkel = getMausWinkel(p1x, p1y, p2x, p2y);

    if ((p1y == p2y) && (p2x > p1x)) { //Wenn 0° erreicht sind
      schwungfaktorX += schwung;
    }

    if ((winkel > 0) && (winkel <= 45)) { //0°-45°
      schwungfaktorX += schwung;
    }

    if ((winkel > 45) && (winkel < 90)) { //45°-90°
      schwungfaktorX += (((p2x - p1x) / (p2y - p1y)) * schwung);
    }

    if ((p1x == p2x) && (p2y > p1y)) { //Wenn 90° erreicht sind
      schwungfaktorX = 0;
    }

    if ((winkel > 90) && (winkel <= 135)) { //90°-135°
      schwungfaktorX -= (((p1x - p2x) / (p2y - p1y)) * schwung);
    }

    if ((winkel > 135) && (winkel < 180)) { //135°-180°
      schwungfaktorX -= schwung;
    }

    if ((p1y == p2y) && (p2x < p1x)) { //Wenn 180° erreicht sind
      schwungfaktorX -= schwung;
      ;
    }

    if ((winkel > 180) && (winkel <= 225)) { //180°-225°
      schwungfaktorX -= schwung;
    }

    if ((winkel > 225) && (winkel < 270)) { //225°-270°
      schwungfaktorX -= (((p1x - p2x) / (p1y - p2y)) * schwung);
    }

    if ((p1x == p2x) && (p2y < p1y)) { // Wenn 270° erreicht sind
      schwungfaktorX = 0;
    }

    if ((winkel > 270) && (winkel <= 315)) { //270°-315°
      schwungfaktorX += (((p2x - p1x) / (p1y - p2y)) * schwung);
    }

    if ((winkel > 315) && (winkel < 360)) { //315°-0°
      schwungfaktorX += schwung;
    }

    return schwungfaktorX;
  } //schwungfaktorX

  public double schwungfaktorY(double p1x, double p1y, double p2x, double p2y,
                               double MousePressedChecker,
                               double schwungfaktorY, double schwung) {
    /*
    In dieser Methode wird der SchwungfaktorY bestimmt. Ein Zahlen Wert der zurück gibt,
    in welche Y-Richtung sich der Queue beim Stoß bewegen soll.
    Dazu dienen die 8.Bedingugnen für jeweils 8 Berreiche um die weiße Kugel,
    damit der Queue gleichmässig zurück gezogen wird.
    */
    double winkel = getMausWinkel(p1x, p1y, p2x, p2y);

    if ((p1y == p2y) && (p2x > p1x)) { //Wenn 0° erreicht sind
      schwungfaktorY = 0;
    }

    if ((winkel > 0) && (winkel <= 45)) { //0°-45°
      schwungfaktorY += (((p2y - p1y) / (p2x - p1x)) * schwung);
    }

    if ((winkel > 45) && (winkel < 90)) { //45°-90°
      schwungfaktorY += schwung;
    }

    if ((p1x == p2x) && (p2y > p1y)) { //Wenn 90° erreicht sind
      schwungfaktorY += schwung;
    }

    if ((winkel > 90) && (winkel <= 135)) { //90°-135°
      schwungfaktorY += schwung;
    }

    if ((winkel > 135) && (winkel < 180)) { //135°-180°
      schwungfaktorY += (((p1y - p2y) / (p2x - p1x)) * schwung);
    }

    if ((p1y == p2y) && (p2x < p1x)) { //Wenn 180° erreicht sind
      schwungfaktorY = 0;
    }

    if ((winkel > 180) && (winkel <= 225)) { //180°-225°
      schwungfaktorY -= (((p1y - p2y) / (p1x - p2x)) * schwung);
    }

    if ((winkel > 225) && (winkel < 270)) { //225°-270°
      schwungfaktorY -= schwung;
    }

    if ((p1x == p2x) && (p2y < p1y)) { // Wenn 270° erreicht sind
      schwungfaktorY -= schwung;
    }

    if ((winkel > 270) && (winkel <= 315)) { //270°-315°
      schwungfaktorY -= schwung;
    }

    if ((winkel > 315) && (winkel < 360)) { //315°-0°
      schwungfaktorY -= (((p2y - p1y) / (p1x - p2x)) * schwung);
    }

    return schwungfaktorY;
  } //schwungfaktorY

  public Graphics bewegenderQueue(Graphics g, Image img, double awinkel,
                                  double x, double y) {
    /*
    Diese Methode dient zum Drehen des Queues img , um einen Punkt mit den Koordinaten x,y
    mit dem Winkel awinkel.
    */
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

    AffineTransform at = AffineTransform.getRotateInstance(((awinkel * Math.PI) / 180),
                                                           x + 10, y + 32);
    g2.setTransform(at);
    g2.drawImage(img, (int) x + 2, (int) y + 22, null);

    return g2;
  } //bewegenderQueue
}
