package UserInterface;

import UserInterface.LogIn.SignIn;

import java.util.ResourceBundle;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;

/**
 * Hauptprogrammklasse zur Ausführung des Projekt
 */
public class Main {
    /**
     * Farbe, die für illegale Nutzereingaben vorbehalten ist
     */
    public static final Color illegalColor = new Color(200, 60, 60);
    /**
     * Erste Akzentfarbe
     */
    public static final Color firstColor = new Color(251, 249, 233);
    /**
     * Zweite Akzentfarbe
     */
    public static final Color secondColor = new Color(0, 50, 100);
    /**
     * Verwendete Schriftart, mit großer Schriftgröße
     */
    public static final Font firstFont = new Font("Microsoft JhengHei UI Bold", Font.PLAIN, 20);
    /**
     * Verwendete Schriftart, mit kleiner Schriftgröße
     */
    public static final Font secondFont = new Font("Microsoft JhengHei UI Bold", Font.PLAIN, 12);
    /**
     * Ressourcen-Buendel, welches die Lokalisierung fuer die derzeit aktive Sprache enthält
     */
    public static ResourceBundle guiBundle;

    /**
     * Main-Methode um das Programm zu starten
     * @param args Kommandozeilenangabe
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignIn();
            }
        });
    }
}
