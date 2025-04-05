package Pokemon.Type;

import java.util.Objects;

/**
 * Speicherung eines Pokemontyps
 */
public class PokemonType {

    /**
     * Name des Pokemontyps
     */
    private String name;

    /**
     * Erstellung eines Pokemontyps, nur von den Containerklassen aufrufbar, zum Sicherstellen, dass kein
     * neuer Typ dazu erfunden wird
     * @param name Name des neuen Typs
     */
    protected PokemonType(String name) {
        setName(name);
    }

    /**
     * Setzen des Namens des Typs, readonly
     * keine Ueberpruefung notwendig, da nur spezifische Typen in Container erstellt werden
     * @param name
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * Rueckgabe des Namens des Typen
     * @return Name des Typen
     */
    public String getName() {
        return name;
    }

    /**
     * Ueberschreibung der equals-Methode
     * Ueberpruefung der Gleichheit des eigenen Objekts mit einem anderen anhand des Namens
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

        PokemonType t = (PokemonType) o;
        return this.name.equals(t.getName());
    }

    /**
     * Ueberschreibung der hashCode-Methode anhand dem Attribut name
     * @return Hash-Wert des Namens
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Ueberschreibung der toString()-Methode zur Ausgabe
     * @return den Namen des Pokemontyps
     */
    @Override
    public String toString() {
        return name;
    }
}
