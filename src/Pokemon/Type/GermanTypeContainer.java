package Pokemon.Type;

import Pokemon.DoesntExistException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Container-Klasse fuer die deutschen Pokemon
 * Orientierung am Singletonmuster
 */
public class GermanTypeContainer implements Iterable<PokemonType> {

    /**
     * Beinhaltet, sofern vorhanden das einzig existierende GermanTypeContainer-Objekt
     */
    private static GermanTypeContainer unique = null;

    /**
     * Speicherung aller deutschen Typen in einer Liste
     */
    private ArrayList<PokemonType> germanTypeList;

    /**
     * Erstellung eines GermanTypeContainers, nur von eigener Klasse aufrufbar
     * Initialisierung der GermanTypeliste und Hinzufuegen aller deutschen Typen
     */
    private GermanTypeContainer() {
        germanTypeList = new ArrayList<PokemonType>();

        germanTypeList.add(new PokemonType("Normal"));
        germanTypeList.add(new PokemonType("Kampf"));
        germanTypeList.add(new PokemonType("Flug"));
        germanTypeList.add(new PokemonType("Gift"));
        germanTypeList.add(new PokemonType("Boden"));
        germanTypeList.add(new PokemonType("Gestein"));
        germanTypeList.add(new PokemonType("Käfer"));
        germanTypeList.add(new PokemonType("Geist"));
        germanTypeList.add(new PokemonType("Stahl"));
        germanTypeList.add(new PokemonType("Feuer"));
        germanTypeList.add(new PokemonType("Wasser"));
        germanTypeList.add(new PokemonType("Pflanze"));
        germanTypeList.add(new PokemonType("Elektro"));
        germanTypeList.add(new PokemonType("Psycho"));
        germanTypeList.add(new PokemonType("Eis"));
        germanTypeList.add(new PokemonType("Drache"));
        germanTypeList.add(new PokemonType("Unlicht"));
        germanTypeList.add(new PokemonType("Fee"));
    }

    /**
     * Ueberpruefung, ob schon ein Objekt der GermanTypeContainer-Klasse existiert,
     * erstellen eines neuen Objekts, wenn bisher keines erstellt wurde
     * @return Einzig existierendes GermanTypeContainer-Objekt
     */
    public static GermanTypeContainer instance() {
        if (unique == null) {
            unique = new GermanTypeContainer();
        }
        return unique;
    }

    /**
     * Rueckgabe des Indexes eines uebergebenen Pokemontyps
     * @param pokemonType Pokemontyp, von dem der Index zurueckgegeben werden soll
     * @return Index vom uebergebenen Pokemontyp in der germanTypeList, sofern vorhanden, sonst -1
     */
    public int getIndexOfPokemonType(PokemonType pokemonType) {
        for (int i = 0; i < germanTypeList.size(); i++) {
            PokemonType type = germanTypeList.get(i);
            if (type.equals(pokemonType)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Rueckgabe des PokemonType-Objekts an i-ter Stelle in der germanTypeList
     * @param index Index, von dem man das PokemonType-Objekt haben will
     * @return PokemonType-Objekt an i-ter Stelle in der germanTypeList
     * @throws IndexOutOfBoundsException Index in der germanTypeList nicht existent
     */
    protected PokemonType getPokemonType(int index) {
        if (index < 0 || index > germanTypeList.size()) {
            throw new IndexOutOfBoundsException("Index of EnglishTypeContainer out of bounds");
        }
        return germanTypeList.get(index);
    }

    /**
     * Rueckgabe des PokemonType-Objekts mit uebergebenen Typnamen
     * @param typeName Typname, von dem man das PokemonType-Objekt haben will
     * @return PokemonType-Objekt mit uebergebenen Typnamen
     * @throws DoesntExistException Kein Typ mit uebergebenen Namen vorhanden
     */
    public PokemonType getPokemonType(String typeName) {
        for (PokemonType pokemonType : germanTypeList) {
            if (pokemonType.getName().equals(typeName)) {
                return pokemonType;
            }
        }
        throw new DoesntExistException("Name isn't a type");
    }

    /**
     * Rueckgabe des entsprechenden PokemonType-Objekts in der englischen Sprache
     * @param pokemonType PokemonType-Objekt, von dem das englische korrulente Objekt gesucht wird
     * @return Das englische korrulente PokemonType-Objekt
     * @throws DoesntExistException pokemonType-Objekt der Eingabe nicht existent
     */
    public PokemonType getEnglishPokemonType(PokemonType pokemonType) {
        int index = getIndexOfPokemonType(pokemonType);

        if (index < 0) {
            throw new DoesntExistException("PokemonType doesn't exist");
        }

        EnglishTypeContainer englishTypeContainer = EnglishTypeContainer.instance();

        return englishTypeContainer.getPokemonType(index);
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
     * Erlauben u.a. einer enhanced-for-Schleife durch die germanTypeList
     * @return Ein Iterator-Objekt mit PokemonType
     */
    @Override
    public Iterator<PokemonType> iterator() {
        return germanTypeList.iterator();
    }

}
