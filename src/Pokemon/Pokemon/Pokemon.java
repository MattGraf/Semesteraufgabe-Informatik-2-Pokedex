package Pokemon.Pokemon;

import javax.swing.ImageIcon;

import java.util.Objects;

import java.io.File;

/**
 * Speicherung eines Pokemon
 */
public class Pokemon {

    /**
     * Konstante zur englischen Spracherkennung
     */
    public final static String ENGLISH = "ENGLISH";
    /**
     * Konstante zur deutschen Spracherkennung
     */
    public final static String GERMAN = "GERMAN";
    /**
     * Konstante zur japanischen Spracherkennung
     */
    public final static String JAPANESE = "JAPANESE";

    /**
     * Auto-Inkrement Attribut um eine einzigartige ID zu erzeugen,
     * welche zur Zuordnung von den verschiedenen Sprachen zu einem Pokemon zustaendig ist
     */
    private static int counter = 0;

    /**
     * ID zur Zuordnung von den erschiedenen Sprachen zu einem Pokemon
     * Zusaetzlich die Pokemon-ID nach dem der Pokedex sortiert ist
     */
    private int id;

    /**
     * ID zur Erkennung von welcher Evolutionsstufe das Pokemon ist
     */
    private int evolution = -1;

    /**
     * Speicherung der Informationen zum Pokemon
     * information[0]: Englisch
     * information[1]: Deutsch
     * information[2]: Japanisch
     */
    private Information[] information;

    /**
     * Speicherung des Bildes, welches in der Pokedex-Tabelle angezeigt wird
     */
    private ImageIcon pixelIcon;


    /**
     * Speicherung des Bildes, welches bei der Informationsanzeige angezeigt wird
     */
    private ImageIcon mainIcon;


    /**
     * Erstellung eines neuen Pokemon mit einer noch nicht existierenden ID ohne Informationen
     */
    public Pokemon(){
        counter++;
        id = counter;
        information = new Information[3];
    }

    /**
     * Setzen der Evolutionsstufe
     * 0: Basis-Pokemon
     * 1: Stufe 1
     * 2: Stufe 2
     * @param evolution Evolutionsstufe des Pokemon
     * @throws IllegalArgumentException Eine nicht existierende Evolutionsstufe
     */
    public void setEvolution(int evolution) throws IllegalArgumentException {
        if (evolution < 0 || evolution > 2) {
            throw new IllegalArgumentException("Illegal Evolution Number");
        }
        this.evolution = evolution;
    }

    /**
     * Hinzufuegen einer Information zum Pokemon
     * @param information Information zum Pokemon
     * @param language Sprache, in welcher die Information verfasst wurde
     * @throws IllegalArgumentException Nicht existierende Sprache
     */
    public void setInformation(Information information, String language) throws IllegalArgumentException {
        switch (language) {
            case ENGLISH -> this.information[0] = information;
            case GERMAN -> this.information[1] = information;
            case JAPANESE -> this.information[2] = information;
            default -> throw new IllegalArgumentException("Language doesn't exist");
        }
    }

    /**
     * Rueckgabe der ID vom Pokemon
     * @return ID des Pokemon
     */
    public int getID() {
        return id;
    }

    /**
     * Rueckgabe der Evolutionsstufe vom Pokemon
     * @return Evolutionsstufe vom Pokemon
     */
    public int getEvolution() {
        return evolution;
    }

    /**
     * Rueckgabe der Information in der gewuenschten Sprache
     * @param language Sprache, von der man die Information haben will
     * @return Information in der gewuenschten Sprache
     * @throws IllegalArgumentException Gewuenschte Sprache existiert nicht
     */
    public Information getInformation(String language) {
        if (information == null) {
            throw new NullPointerException("It doesn't exist any information");
        } else if (language.equals(ENGLISH)) {
            return information[0];
        } else if (language.equals(GERMAN)) {
            return information[1];
        } else if (language.equals(JAPANESE)) {
            return information[2];
        } else {
            throw new IllegalArgumentException("Language doesn't exist");
        }
    }

    /**
     * Rueckgabe der ID vom Pokemon ueber die Information
     * @return ID vom Pokemon
     */
    public int getPokemonID() {
        int pokemonID = 0;
        if (this.getInformation("ENGLISH") != null) {
            pokemonID = this.getInformation("ENGLISH").getPokemonID();
        } else if (this.getInformation("GERMAN") != null) {
            pokemonID = this.getInformation("GERMAN").getPokemonID();
        } else if (this.getInformation("JAPANESE") != null) {
            pokemonID = this.getInformation("JAPANESE").getPokemonID();
        }
        return pokemonID;
    }

    /**
     * Setzen des Bildes fuer die Pokemon-Tabelle anhand eines Pfades
     * @param path Pfad, unter dem das Bild gespeichert ist
     * @return Erstellen eines ImageIcon mit existierendem Pfad
     */
    public boolean setPixelIcon(String path) {
        try {
            File f = new File(path);
            if (!f.exists() || f.isDirectory()) {
                return false;
            }
            pixelIcon = new ImageIcon(path);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Setzen des Bildes fuer die Pokemon-Tabelle anhand eines anderen Bildes
     * @param pixelIcon Bild, das zugeordnet werden soll
     * @throws NullPointerException Wenn kein Bild uebergeben wurde
     */
    public void setPixelIcon(ImageIcon pixelIcon) throws NullPointerException {
        if (pixelIcon == null) {
            throw new NullPointerException("Illegal PixelIcon");
        }

        this.pixelIcon = pixelIcon;
    }

    /**
     * Setzen des Bildes fuer die Informationsanzeige anhand eines Pfades
     * @param path Pfad, unter dem das Bild gespeichert ist
     * @return Erstellen eines ImageIcon mit existierendem Pfad
     */
    public boolean setMainIcon(String path) {
        try {
            File f = new File(path);
            if (!f.exists() || f.isDirectory()) {
                return false;
            }
            mainIcon = new ImageIcon(path);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }


    /**
     * Setzen des Bildes fuer die Informationsanzeige anhand eines anderen Bildes
     * @param mainIcon Bild, das zugeordnet werden soll
     * @throws NullPointerException Wenn kein Bild uebergeben wurde
     */
    public void setMainIcon(ImageIcon mainIcon) throws NullPointerException {
            if (mainIcon == null) {
                throw new NullPointerException("Illagel MainIcon");
            }

            this.mainIcon = mainIcon;
    }

    /**
     * Rueckgabe des Bildes fuer die Pokemon-Tabelle
     * @return Bild fuer die Pokemon-Tabelle, falls existent
     * @throws NullPointerException Kein Bild zugeordnet
     */
    public ImageIcon getPixelIcon() throws NullPointerException {
        if (pixelIcon == null) {
            throw new NullPointerException("PixelIcon doesn't exist");
        }
        return pixelIcon;
    }

    /**
     * Rueckgabe des Bildes fuer die Informationsanzeige
     * @return Bild fuer die Informationsanzeige, falls existent
     * @throws NullPointerException Kein Bild zugeordnet
     */
    public ImageIcon getMainIcon() throws NullPointerException {
        if (mainIcon == null) {
            throw new NullPointerException("MainIcon doesn't exist");
        }
        return mainIcon;
    }

    /**
     * Rueckgabe, ob zu dem Pokemon in der uebergebenen Sprache Informationen zugeordnet sind
     * @param language Sprache, welche man ueberpruefen will
     * @return Informationen zum Pokemon in uebergebenen Sprache existent
     */
    public boolean isInformation(String language) {
        boolean erg = false;
        switch(language) {
            case ENGLISH -> erg = information[0] != null;
            case GERMAN -> erg = information[1] != null;
            case JAPANESE -> erg = information[2] != null;
        }
        return erg;
    }

    /**
     * Ueberschreibung der hashCode-Methode anhand dem Attribut id
     * @return Hash-Wert der ID
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Ueberschreibung der equals-Methode
     * Ueberpruefung der Gleichheit des eigenen Objekts mit einem anderen anhand der ID
     * @param o Zu vergleichendes Objekt
     * @return Gleichheit vom uebergebenen mit eigenem Objekt
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (!o.getClass().equals(this.getClass())) {
            return false;
        }

        Pokemon p = (Pokemon) o;
        return this.id == p.getID();
    }

    /**
     * Rueckgabe des Auto-Inkrement-Attributs fuer die Pokemon-ID
     * @return Auto-Inkrement-Attribut fuer die Pokemon-ID
     */
    public static int getCounter() {
        return counter;
    }

    /**
     * Zuruecksetzen des Auto-Inkrement-Attributs fuer die Pokemon-ID
     */
    public static void resetCounter() {
        counter = 0;
    }

    /**
     * Reduzieren des Counters um 1
     */
    public static void counterDecrement() {
        counter--;
    }

    /**
     * Reduzieren der ID um 1 fuers Loeschen von Pokemon
     * Nur Aufrufbar innerhalb der Pokemonklassen
     */
    protected void idDecrement() {
        id--;
        for (int i = 0; i < 3; i++) {
            if (information[i] != null) {
                information[i].setPokemonID(id);
            }
        }
    }
}
