package Pokemon.Pokemon;

import Pokemon.CharSupport;
import Pokemon.Type.PokemonType;

import java.util.Objects;

/**
 * Speicherung der Informationen der Pokemon, getrennt von Pokemon u.a. wegen den Sprachen
 */
public class Information implements CharSupport {

    /**
     * Hochzaehlen des Counters zum spezifischeren Vergleich zweier Informationen
     */
    private static int counter = 0;

    /**
     * Id, die durch den Counter bestimmt wird
     */
    private final int id;

    /**
     * Speicherung der pokemonId zur Zuordnung der Information zum Pokemon
     */
    private int pokemonID;

    /**
     * Name des Pokemon
     */
    private String name;

    /**
     * List der Typen des Pokemon
     * type[0]: Ersttyp
     * type[1]: Zweittyp
     */
    private PokemonType[] type = new PokemonType[2];

    /**
     * Erstellung einer Information anhand...
     * @param pokemonID ID des Pokemon, zu dem die Information gehoert
     * @param name Name des zugehoerigen Pokemon
     * @param firstType Erster Typ vom Pokemon -> Pflichttyp (Zweittyp nicht notwendig)
     * @param language Sprache, in welcher die Information erfasst wird
     * @throws IllegalArgumentException Ungueltiger Name
     */
    public Information(int pokemonID, String name, PokemonType firstType, String language) throws IllegalArgumentException {
        counter++;
        id = counter;
        this.pokemonID = pokemonID;
        switch (language) {
            case "ENGLISH" -> setEnglishName(name);
            case "GERMAN" -> setGermanName(name);
            case "JAPANESE" -> setJapaneseName(name);
            default -> throw new IllegalArgumentException("Illegal Language in Information");
        }
        this.type[0] = firstType;
    }

    /**
     * Erstellung einer Information anhand ...
     * @param pokemonID ID des Pokemon, zu dem die Information gehoert
     * @param name Name des zugehoerigen Pokemon
     * @param firstType Erster Typ vom Pokemon -> Pflichttyp
     * @param secondType Zweiter Typ vom Pokemon
     * @param language Sprache, in welcher die Information erfasst wird
     * @throws IllegalArgumentException Ungueltiger Name
     */
    public Information(int pokemonID, String name, PokemonType firstType, PokemonType secondType, String language) throws IllegalArgumentException {
        this(pokemonID, name, firstType, language);
        type[1] = secondType;
    }

    /**
     * Setzen des Namens abhaengig von Sprache
     * Leerzeichen vor dem ersten und nach dem letzten anderen Zeichen werden geloescht
     * Englisch:
     * Beginnend mit Grossbuchstaben
     * Kombination aus Buchstaben und Ziffern und hoechstens einem Leerzeichen
     * Enden mit Buchstabe oder Ziffer
     * @param name Name, der der englischen Sprache zugeordnet werden soll
     * @throws IllegalArgumentException Mitgabe einer Zeichenkette, die die vorhergenannte Bedingungen nicht erfuellt
     */
    public void setEnglishName(String name) throws IllegalArgumentException {
        name = name.strip();
        if (!name.matches("[A-Z][A-Za-z0-9. ]+[A-Za-z0-9]") && count(name, ' ') > 1) {
            throw new IllegalArgumentException("Illegal Information Name - English");
        }
        this.name = name;
    }

    /**
     * Setzen des Namens abhaengig von Sprache
     * Leerzeichen vor dem ersten und nach dem letzten anderen Zeichen werden geloescht
     * Deutsch:
     * Beginnend mit Grossbuchstaben
     * Kombination aus Buchstaben, Umlaute und Ziffern und hoechstens einem Leerzeichen
     * Enden mit Buchstabe oder Ziffer
     * @param name Name, der der deutschen Sprache zugeordnet werden soll
     * @throws IllegalArgumentException Mitgabe einer Zeichenkette, die die vorhergenannte Bedingungen nicht erfuellt
     */
    public void setGermanName(String name) throws IllegalArgumentException {
        name = name.strip();
        if (!name.matches("[A-Z][A-Za-z0-9.äöü ]+[A-Za-z0-9]") && count(name, ' ') > 1) {
            throw new IllegalArgumentException("Illegal Information Name - German");
        }
        this.name = name;
    }

    /**
     * Setzen des Namens abhaengig von Sprache
     * Leerzeichen vor dem ersten und nach dem letzten anderen Zeichen werden geloescht
     * Japanisch:
     * Hoechstens ein Leerzeichen zwischen anderen Zeichen
     * @param name Name, der der japanischen Sprache zugeordnet werden soll
     * @throws IllegalArgumentException Mitgabe einer Zeichenkette, die die vorhergenannte Bedingungen nicht erfuellt
     */
    public void setJapaneseName(String name) throws IllegalArgumentException {
        name = name.strip();
        if (count(name, ' ') > 1) {
            throw new IllegalArgumentException("Illegal Information Name - Japanese");
        }
        this.name = name;
    }

    /**
     * Ueberpruefung, ob die Information einen ersten Typ besitzt
     * @return Wahrheitswert, ob Information einen ersten Typ besitzt
     */
    public boolean hasFirstType() {
        return type[0] != null;
    }

    /**
     * Ueberpruefung, ob die Information einen zweiten Typ besitzt
     * @return Wahrheitswert, ob Information einen zweiten Typ besitzt
     */
    public boolean hasSecondType() {
        return type[1] != null;
    }

    /**
     * Rueckgabe des ersten Typs, sofern vorhanden
     * @return Erster Typ
     * @throws NullPointerException Erster Typ nicht belegt
     */
    public PokemonType getFirstType() throws NullPointerException {
        if (!hasFirstType()) {
            throw new NullPointerException("First Type doesn't exist");
        }
        return type[0];
    }

    /**
     * Rueckgabe des zweiten Typs, sofern vorhanden
     * @return Zweiter Typ
     * @throws NullPointerException Zweiter Typ nicht belegt
     */
    public PokemonType getSecondType() throws NullPointerException {
        if (!hasSecondType()) {
            throw new NullPointerException("Second Type doesn't exist");
        }
        return type[1];
    }

    /**
     * Rueckgabe der ID des Pokemon, zu dem die Information gehoert
     * @return ID des Pokemon, zu dem die Information gehoert
     */
    public int getPokemonID() {
        return pokemonID;
    }

    /**
     * Rueckgabe der ID der Information
     * @return ID der Information
     */
    public int getID() {
        return id;
    }

    /**
     * Rueckgabe des Namens des Pokemon der jeweiligen Sprache
     * @return Name des Pokemon der jeweiligen Sprache
     */
    public String getName() {
        return name;
    }

    /**
     * Ueberschreibung der hashCode-Methoden aus Object anhand der ID
     * @return Hash Code Wert fuer das Objekt der Informationsklasse
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Ueberschreibung der equals-Methode aus Object anhand der ID
     * @return Gleichheit der ID der eigenen Information und der uebergebenen
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (!o.getClass().equals(this.getClass())) {
            return false;
        }

        Information i = (Information) o;

        return this.id == i.getID();
    }

    /**
     * Neusetzen der PokemonId zur Erneuerung der Verbindung von Pokemon und dieser Information
     * @param pokemonID Neue Pokemon
     */
    protected void setPokemonID(int pokemonID) {
        this.pokemonID = pokemonID;
    }

}
