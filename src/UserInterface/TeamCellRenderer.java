package UserInterface;

import User.TeamContainer;
import Pokemon.Pokemon.PokemonContainer;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ImageIcon;

/**
 * Erweiterung des ListCellRenderers, welcher zur benutzerdefinierten Darstellung der teamList verwendet wird
 */
public class TeamCellRenderer extends JPanel implements ListCellRenderer<Integer> {
    /**
     * Hintergrundbild, das angezeigt wird, wenn die Zelle ausgewählt ist
     */
    private static BufferedImage activeCell;
    /**
     * Hintergrundbild, das angezeigt wird, wenn die Zelle nicht ausgewählt ist
     */
    private static BufferedImage inactiveCell;
    /**
     * Zelle bzw. Listeneintrag, auf welchem die Informationen eines Objekts dargestellt werden
     */
    private final ImagePanel cellPanel;
    /**
     * Label, das das Icon des Pokemon enthält
     */
    private final JLabel iconLabel;

    /**
     * Konstruktor zur Belegung einer Zelle der teamList
     */
    public TeamCellRenderer() {
        cellPanel = new ImagePanel(getInactiveCell());
        cellPanel.setLayout(new BorderLayout());

        iconLabel = new JLabel();
        cellPanel.add(iconLabel, BorderLayout.CENTER);

        this.setBackground(new Color(0, 0, 0, 0));
        this.add(cellPanel);
    }

    /**
     * Renderer, welcher die benötigten Eingenschaften der Zelle festlegt
     * @param list Die JList, die wir zeichnen
     * @param number Der von teamList.getModel().getElementAt(index) zurueckgegebene Wert
     * @param index Der Index der Zelle
     * @param isSelected Wahr, wenn die angegebene Zelle ausgewählt wurde
     * @param cellHasFocus Wahr, wenn die angegebene Zelle im Fokus ist
     * @return Die derzeitige Zelle
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends Integer> list,
                                                  Integer number, int index, boolean isSelected, boolean cellHasFocus) {
        setBackground(new Color(0, 0, 0, 0));

        if (isSelected) {
            cellPanel.setImage(getActiveCell());
        } else {
            cellPanel.setImage(getInactiveCell());
        }

        TeamContainer tc = TeamContainer.instance();
        PokemonContainer pc = PokemonContainer.instance();

        if (tc.getPokemon(number) != -1) {
            try {
                //pixelIcon von [120x120]px auf [60x60]px transformieren
                Image originalImage = pc.getPokemonByID(tc.getPokemon(number)).getPixelIcon().getImage();
                BufferedImage resizedImage = new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = resizedImage.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(originalImage, 0, 0, 60, 60, null);
                g2.dispose();
                iconLabel.setIcon(new ImageIcon(resizedImage));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            iconLabel.setIcon(null);
        }

        return this;
    }

    /**
     * Bereitstellung des aktiven Hintergrundbilds
     * @return aktives Hintergrundbild
     */
    private static BufferedImage getActiveCell() {
        if (activeCell == null) {
            try {
                activeCell = ImageIO.read(new File(System.getProperty("user.dir") + "/src/UserInterface/res/ui/circle_active.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return activeCell;
    }

    /**
     * Bereitstellung des inaktiven Hintergrundbilds
     * @return inaktives Hintergrundbild
     */
    private static BufferedImage getInactiveCell() {
        if (inactiveCell == null) {
            try {
                inactiveCell = ImageIO.read(new File(System.getProperty("user.dir") + "/src/UserInterface/res/ui/circle_inactive.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inactiveCell;
    }
}
