package Pokemon.Type;

import Pokemon.DoesntExistException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Container-Klasse fuer die japanischen Pokemon
 * Orientierung am Singletonmuster
 */
public class JapaneseTypeContainer implements Iterable<PokemonType> {

    /**
     * Beinhaltet, sofern vorhanden das einzig existierende JapaneseTypeContainer-Objekt
     */
    private static JapaneseTypeContainer unique = null;

    /**
     * Speicherung aller japanischen Typen in einer Liste
     */
    private ArrayList<PokemonType> japaneseTypeList;

    /**
     * Erstellung eines JapaneseTypeContainers, nur von eigener Klasse aufrufbar
     * Initialisierung der JapaneseTypeliste und Hinzufuegen aller japanischen Typen
     */
    private JapaneseTypeContainer() {
        japaneseTypeList = new ArrayList<PokemonType>();

        japaneseTypeList.add(new PokemonType("ノーマル"));
        japaneseTypeList.add(new PokemonType("かくとう"));
        japaneseTypeList.add(new PokemonType("ひこう"));
        japaneseTypeList.add(new PokemonType("どく"));
        japaneseTypeList.add(new PokemonType("じめん"));
        japaneseTypeList.add(new PokemonType("いわ"));
        japaneseTypeList.add(new PokemonType("むし"));
        japaneseTypeList.add(new PokemonType("ゴースト"));
        japaneseTypeList.add(new PokemonType("はがね"));
        japaneseTypeList.add(new PokemonType("ほのお"));
        japaneseTypeList.add(new PokemonType("みず"));
        japaneseTypeList.add(new PokemonType("くさ"));
        japaneseTypeList.add(new PokemonType("でんき"));
        japaneseTypeList.add(new PokemonType("エスパー"));
        japaneseTypeList.add(new PokemonType("こおり"));
        japaneseTypeList.add(new PokemonType("ドラゴン"));
        japaneseTypeList.add(new PokemonType("あく"));
        japaneseTypeList.add(new PokemonType("フェアリー"));
    }

    /**
     * Ueberpruefung, ob schon ein Objekt der JapaneseTypeContainer-Klasse existiert,
     * erstellen eines neuen Objekts, wenn bisher keines erstellt wurde
     * @return Einzig existierendes JapaneseTypeContainer-Objekt
     */
    public static JapaneseTypeContainer instance() {
        if (unique == null) {
            unique = new JapaneseTypeContainer();
        }
        return unique;
    }

    /**
     * Rueckgabe des Indexes eines uebergebenen Pokemontyps
     * @param pokemonType Pokemontyp, von dem der Index zurueckgegeben werden soll
     * @return Index vom uebergebenen Pokemontyp in der japaneseTypeList, sofern vorhanden, sonst -1
     */
    private int getIndexOfPokemonType(PokemonType pokemonType) {
        for (int i = 0; i < japaneseTypeList.size(); i++) {
            PokemonType type = japaneseTypeList.get(i);
            if (type.equals(pokemonType)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Rueckgabe des PokemonType-Objekts an i-ter Stelle in der japaneseTypeList
     * @param index Index, von dem man das PokemonType-Objekt haben will
     * @return PokemonType-Objekt an i-ter Stelle in der japaneseTypeList
     * @throws IndexOutOfBoundsException Index in der japaneseTypeList nicht existent
     */
    protected PokemonType getPokemonType(int index) {
        if (index < 0 || index > japaneseTypeList.size()) {
            throw new IndexOutOfBoundsException("Index of EnglishTypeContainer out of bounds");
        }
        return japaneseTypeList.get(index);
    }

    /**
     * Rueckgabe des PokemonType-Objekts mit uebergebenen Typnamen
     * @param typeName Typname, von dem man das PokemonType-Objekt haben will
     * @return PokemonType-Objekt mit uebergebenen Typnamen
     * @throws DoesntExistException Kein Typ mit uebergebenen Namen vorhanden
     */
    public PokemonType getPokemonType(String typeName) {
        for (PokemonType pokemonType : japaneseTypeList) {
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
     * Ueberschreiben der iterator-Methode der Iterable-Schnittstelle
     * Erlauben u.a. einer enhanced-for-Schleife durch die japaneseTypeList
     * @return Ein Iterator-Objekt mit PokemonType
     */
    @Override
    public Iterator<PokemonType> iterator() {
        return japaneseTypeList.iterator();
    }

}
