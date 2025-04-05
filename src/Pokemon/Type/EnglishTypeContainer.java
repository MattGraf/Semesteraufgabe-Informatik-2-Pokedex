package Pokemon.Type;

import Pokemon.DoesntExistException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Container-Klasse fuer die englischen Pokemon
 * Orientierung am Singletonmuster
 */
public class EnglishTypeContainer implements Iterable<PokemonType> {

    /**
     * Beinhaltet, sofern vorhanden das einzig existierende EnglishTypeContainer-Objekt
     */
    private static EnglishTypeContainer unique = null;

    /**
     * Speicherung aller englischen Typen in einer Liste
     */
    private ArrayList<PokemonType> englishTypeList;

    /**
     * Erstellung eines EnglishTypeContainers, nur von eigener Klasse aufrufbar
     * Initialisierung der EnglishTypeliste und Hinzufuegen aller englischen Typen
     */
    private EnglishTypeContainer() {
        englishTypeList = new ArrayList<PokemonType>();

        englishTypeList.add(new PokemonType("Normal"));
        englishTypeList.add(new PokemonType("Fighting"));
        englishTypeList.add(new PokemonType("Flying"));
        englishTypeList.add(new PokemonType("Poison"));
        englishTypeList.add(new PokemonType("Ground"));
        englishTypeList.add(new PokemonType("Rock"));
        englishTypeList.add(new PokemonType("Bug"));
        englishTypeList.add(new PokemonType("Ghost"));
        englishTypeList.add(new PokemonType("Steel"));
        englishTypeList.add(new PokemonType("Fire"));
        englishTypeList.add(new PokemonType("Water"));
        englishTypeList.add(new PokemonType("Grass"));
        englishTypeList.add(new PokemonType("Electric"));
        englishTypeList.add(new PokemonType("Psychic"));
        englishTypeList.add(new PokemonType("Ice"));
        englishTypeList.add(new PokemonType("Dragon"));
        englishTypeList.add(new PokemonType("Dark"));
        englishTypeList.add(new PokemonType("Fairy"));
    }

    /**
     * Ueberpruefung, ob schon ein Objekt der EnglishTypeContainer-Klasse existiert,
     * erstellen eines neuen Objekts, wenn bisher keines erstellt wurde
     * @return Einzig existierendes EnglishTypeContainer-Objekt
     */
    public static EnglishTypeContainer instance() {
        if (unique == null) {
            unique = new EnglishTypeContainer();
        }
        return unique;
    }

    /**
     * Rueckgabe des Indexes eines uebergebenen Pokemontyps
     * @param pokemonType Pokemontyp, von dem der Index zurueckgegeben werden soll
     * @return Index vom uebergebenen Pokemontyp in der englishTypeList, sofern vorhanden, sonst -1
     */
    private int getIndexOfPokemonType(PokemonType pokemonType) {
        for (int i = 0; i < englishTypeList.size(); i++) {
            PokemonType type = englishTypeList.get(i);
            if (type.equals(pokemonType)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Rueckgabe des PokemonType-Objekts an i-ter Stelle in der englishTypeList
     * @param index Index, von dem man das PokemonType-Objekt haben will
     * @return PokemonType-Objekt an i-ter Stelle in der englishTypeList
     * @throws IndexOutOfBoundsException Index in der englishTypeList nicht existent
     */
    protected PokemonType getPokemonType(int index) {
        if (index < 0 || index > englishTypeList.size()) {
            throw new IndexOutOfBoundsException("Index of EnglishTypeContainer out of bounds");
        }
        return englishTypeList.get(index);
    }

    /**
     * Rueckgabe des PokemonType-Objekts mit uebergebenen Typnamen
     * @param typeName Typname, von dem man das PokemonType-Objekt haben will
     * @return PokemonType-Objekt mit uebergebenen Typnamen
     * @throws DoesntExistException Kein Typ mit uebergebenen Namen vorhanden
     */
    public PokemonType getPokemonType(String typeName) {
        for (PokemonType pokemonType : englishTypeList) {
            if (pokemonType.getName().equals(typeName)) {
                return pokemonType;
            }
        }
        throw new DoesntExistException("Name isn't a type");
    }

    /**
     * Rueckgabe des entsprechenden PokemonType-Objekts in der deutschen Sprache
     * @param pokemonType PokemonType-Objekt, von dem das deutsche korrulente Objekt gesucht wird
     * @return Das deutsche korrulente PokemonType-Objekt
     * @throws DoesntExistException pokemonType-Objekt der Eingabe nicht existent
     */
    public PokemonType getGermanPokemonType(PokemonType pokemonType) {
        int index = getIndexOfPokemonType(pokemonType);

        if (index < 0) {
            throw new DoesntExistException("PokemonType doesn't exist");
        }

        GermanTypeContainer germanTypeContainer = GermanTypeContainer.instance();

        return germanTypeContainer.getPokemonType(index);
    }

    /**
     * Rueckgabe des entsprechenden PokemonType-Objekts in der japanischen Sprache
     * @param pokemonType PokemonType-Objekt, von dem das japanische korrulente Objekt gesucht wird
     * @return Das japanische korrulente PokemonType-Objekt
     * @throws DoesntExistException pokemonType-Objekt der Eingabe nicht existent
     */
    public PokemonType getJapanesePokemonType(PokemonType pokemonType) {
        int index = getIndexOfPokemonType(pokemonType);

        if (index < 0) {
            throw new DoesntExistException("PokemonType doesn't exist");
        }

        JapaneseTypeContainer japaneseTypeContainer = JapaneseTypeContainer.instance();

        return japaneseTypeContainer.getPokemonType(index);
    }

    /**
     * Ueberschreiben der iterator-Methode der Iterable-Schnittstelle
     * Erlauben u.a. einer enhanced-for-Schleife durch die englishTypeList
     * @return Ein Iterator-Objekt mit PokemonType
     */
    @Override
    public Iterator<PokemonType> iterator() {
        return englishTypeList.iterator();
    }

}
