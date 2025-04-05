package UserInterface;

import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * Bereitstellung von Farben und Symbole der Typen
 */
public enum typeAssets {
    /**
     * Farbe und Bild des Typs Bug
     */
    BUG(new Color(144, 193, 44), new ImageIcon(Constants.TYPE_DIR + "bug.png")),
    /**
     * Farbe und Bild des Typs Dark
     */
    DARK(new Color(90, 83, 102), new ImageIcon(Constants.TYPE_DIR + "dark.png")),
    /**
     * Farbe und Bild des Typs Dragon
     */
    DRAGON(new Color(10, 109, 196), new ImageIcon(Constants.TYPE_DIR + "dragon.png")),
    /**
     * Farbe und Bild des Typs Electric
     */
    ELECTRIC(new Color(243, 210, 59), new ImageIcon(Constants.TYPE_DIR + "electric.png")),
    /**
     * Farbe und Bild des Typs Fairy
     */
    FAIRY(new Color(236, 143, 230), new ImageIcon(Constants.TYPE_DIR + "fairy.png")),
    /**
     * Farbe und Bild des Typs Fighting
     */
    FIGHTING(new Color(206, 64, 105), new ImageIcon(Constants.TYPE_DIR + "fighting.png")),
    /**
     * Farbe und Bild des Typs Fire
     */
    FIRE(new Color(255, 156, 84), new ImageIcon(Constants.TYPE_DIR + "fire.png")),
    /**
     * Farbe und Bild des Typs Flying
     */
    FLYING(new Color(143, 168, 221), new ImageIcon(Constants.TYPE_DIR + "flying.png")),
    /**
     * Farbe und Bild des Typs Ghost
     */
    GHOST(new Color(82, 105, 172), new ImageIcon(Constants.TYPE_DIR + "ghost.png")),
    /**
     * Farbe und Bild des Typs Grass
     */
    GRASS(new Color(99, 187, 91), new ImageIcon(Constants.TYPE_DIR + "grass.png")),
    /**
     * Farbe und Bild des Typs Ground
     */
    GROUND(new Color(217, 119, 70), new ImageIcon(Constants.TYPE_DIR + "ground.png")),
    /**
     * Farbe und Bild des Typs Ice
     */
    ICE(new Color(116, 206, 192), new ImageIcon(Constants.TYPE_DIR + "ice.png")),
    /**
     * Farbe und Bild des Typs Normal
     */
    NORMAL(new Color(144, 153, 161), new ImageIcon(Constants.TYPE_DIR + "normal.png")),
    /**
     * Farbe und Bild des Typs Poison
     */
    POISON(new Color(171, 106, 200), new ImageIcon(Constants.TYPE_DIR + "poison.png")),
    /**
     * Farbe und Bild des Typs Psychic
     */
    PSYCHIC(new Color(249, 113, 118), new ImageIcon(Constants.TYPE_DIR + "psychic.png")),
    /**
     * Farbe und Bild des Typs Rock
     */
    ROCK(new Color(199, 183, 139), new ImageIcon(Constants.TYPE_DIR + "rock.png")),
    /**
     * Farbe und Bild des Typs Steel
     */
    STEEL(new Color(90, 142, 161), new ImageIcon(Constants.TYPE_DIR + "steel.png")),
    /**
     * Farbe und Bild des Typs Water
     */
    WATER(new Color(77, 144, 213), new ImageIcon(Constants.TYPE_DIR + "water.png"));

    /**
     * Bereitstellung von Konstanten zur Verwendung fuer die Deklaration der Enum-Konstanten
     */
    private static class Constants {
        /**
         * Pfad der den Speicherort der type-Symbole angibt
         */
        private static final String TYPE_DIR = System.getProperty("user.dir") + "/src/UserInterface/res/type/";
    }

    /**
     * Farbe des Typs
     */
    private Color color;
    /**
     * Symbol des Typs
     */
    private ImageIcon icon;

    /**
     * Konstruktor zur Erstellung von Enum-Konstanten, die einen Pokemon-Typ beschreiben
     * @param color Farbe des Typs
     * @param icon Symbol des Typs
     */
    typeAssets(Color color, ImageIcon icon) {
        this.color = color;
        this.icon = icon;
    }

    /**
     *
     * @return Farbe des Typs
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @return Symbol des Typs
     */
    public ImageIcon getIcon() {
        return icon;
    }
}
