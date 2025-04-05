package UserInterface;

import Pokemon.Pokemon.Information;
import Pokemon.Pokemon.Pokemon;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.Color;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

/**
 * Erweiterung des ListCellRenderers, welcher zur benutzerdefinierten Darstellung der pokemonList verwendet wird
 */
public class PokemonCellRenderer extends JPanel implements ListCellRenderer<Pokemon> {
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
     * Label, das die ID des Pokemon enthält
     */
    private final JLabel idLabel;
    /**
     * Label, das den Namen des Pokemon enthält
     */
    private final JLabel nameLabel;

    /**
     * Konstruktor zur Belegung einer Zelle der pokemonList
     */
    public PokemonCellRenderer() {
        cellPanel = new ImagePanel(getInactiveCell());
        cellPanel.setLayout(new BorderLayout());

        iconLabel = new JLabel();
        idLabel = new JLabel();
        nameLabel = new JLabel();

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.add(idLabel);
        textPanel.add(nameLabel);
        textPanel.setOpaque(false);

        cellPanel.add(iconLabel, BorderLayout.WEST);
        cellPanel.add(textPanel, BorderLayout.CENTER);

        this.setBackground(new Color(0, 0, 0, 0));
        this.add(cellPanel);
    }

    /**
     * Renderer, welcher die benötigten Eingenschaften der Zelle festlegt
     * @param list Die JList, die wir zeichnen
     * @param pokemon Der von pokemonList.getModel().getElementAt(index) zurueckgegebene Wert
     * @param index Der Index der Zelle
     * @param isSelected Wahr, wenn die angegebene Zelle ausgewählt wurde
     * @param cellHasFocus Wahr, wenn die angegebene Zelle im Fokus ist
     * @return Die derzeitige Zelle
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends Pokemon> list,
                                                  Pokemon pokemon, int index, boolean isSelected, boolean cellHasFocus) {
        setBackground(new Color(0, 0, 0, 0));

        if (isSelected) {
            idLabel.setForeground(Main.secondColor);
            nameLabel.setForeground(Main.secondColor);
            cellPanel.setImage(getActiveCell());
        } else {
            idLabel.setForeground(Main.firstColor);
            nameLabel.setForeground(Main.firstColor);
            cellPanel.setImage(getInactiveCell());
        }

        try {
            iconLabel.setIcon(pokemon.getPixelIcon());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        int pokemonID = pokemon.getPokemonID();

        Information temp;

        switch (Locale.getDefault().toString()) {
            case ("en") -> {
                temp = pokemon.getInformation("ENGLISH");
                idLabel.setText("No. " + String.format("%03d", pokemonID));
                if (temp != null) {
                    nameLabel.setText(pokemon.getInformation("ENGLISH").getName());
                } else {
                    nameLabel.setText("");
                }
            }
            case ("de") -> {
                temp = pokemon.getInformation("GERMAN");
                idLabel.setText("Nr. " + String.format("%03d", pokemonID));
                if (temp != null) {
                    nameLabel.setText(pokemon.getInformation("GERMAN").getName());
                } else {
                    nameLabel.setText("");
                }
            }
            case ("ja") -> {
                temp = pokemon.getInformation("JAPANESE");
                idLabel.setText("No. " + String.format("%03d", pokemonID));
                if (temp != null) {
                    nameLabel.setText(pokemon.getInformation("JAPANESE").getName());
                } else {
                    nameLabel.setText("");
                }
            }
            default -> System.err.println("Locale-Error with:" + Locale.getDefault());
        }

        idLabel.setFont(Main.firstFont);
        nameLabel.setFont(Main.firstFont);
        idLabel.setBorder(new EmptyBorder(20,8,0,0));
        nameLabel.setBorder(new EmptyBorder(0,8,20,0));

        return this;
    }

    /**
     * Bereitstellung des aktiven Hintergrundbilds
     * @return aktives Hintergrundbild
     */
    private static BufferedImage getActiveCell() {
        if (activeCell == null) {
            try {
                activeCell = ImageIO.read(new File(System.getProperty("user.dir") + "/src/UserInterface/res/ui/cell_active.png"));
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
                inactiveCell = ImageIO.read(new File(System.getProperty("user.dir") + "/src/UserInterface/res/ui/cell_inactive.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inactiveCell;
    }
}
