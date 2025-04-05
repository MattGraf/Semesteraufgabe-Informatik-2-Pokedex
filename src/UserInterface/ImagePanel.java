package UserInterface;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Erweiterung der JPanel-Klasse, welche es ermöglicht fuer Objekten jener Klasse ein Hintergrundbild festzulegen
 */
public class ImagePanel extends JPanel{
    /**
     * Zu zeichnendes Bild
     */
    private Image image;
    /**
     * Dimension des zu zeichnenden Bildes, in Pixel
     */
    private Dimension drawSize;

    /**
     * Konstruktor zur Erstellung eines ImagePanels
     * @param image Bild, das in das JPanel gezeichnet wird
     */
    public ImagePanel(Image image) {
        super();
        setImage(image);
        setOpaque(false);
        setImage(image);
    }

    /**
     * Konstruktor zur Erstellung eines ImagePanels
     * @param path Pfad des Bildes, das in das JPanel gezeichnet wird
     */
    public ImagePanel(String path) {
        super();
        setImage(path);
        setDrawSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        setOpaque(false);
        setImage(path);
    }

    /**
     *
     * @param image Zu zeichnendes Bild
     */
    public void setImage(Image image) {
        this.image = image;
        setDrawSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        repaint();
    }

    /**
     *
     * @param path Pfad des Bildes, das in das JPanel gezeichnet wird
     */
    public void setImage(String path) {
        try {
            setImage(ImageIO.read(new File(path)));
            setDrawSize(new Dimension(image.getWidth(null), image.getHeight(null)));
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param drawSize Dimension des zu zeichnenden Bildes, in Pixel
     */
    public void setDrawSize(Dimension drawSize) {
        this.drawSize = drawSize;
    }

    /**
     * ueberschreibung der paintComponent-Methode um drawImage aufzurufen
     * @param g Grafik-Objekt, auf das gezeichnet wird
     */
    @Override
    public void paintComponent(Graphics g) {
        if (image != null)
            drawImage(g);
    }

    /**
     * Bereitstellung der gewuenschten Dimension des ImagePanel
     * @return Dimension, auf die das Bild gezeichnet wird
     */
    @Override
    public Dimension getPreferredSize() {
        return drawSize;
    }

    /**
     * Zeichnet das Bild auf das Grafik-Objekt in der durch drawSize festgelegten Größe
     * @param g Grafik-Objekt, auf das gezeichnet wird
     */
    public void drawImage(Graphics g) {
        g.drawImage(image, 0, 0, (int) drawSize.getWidth(), (int) drawSize.getHeight(), null);
    }
}
