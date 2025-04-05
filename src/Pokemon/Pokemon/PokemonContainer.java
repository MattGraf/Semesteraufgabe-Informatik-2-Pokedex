package Pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import Pokemon.AlreadyExistException;
import Pokemon.DoesntExistException;

/**
 * Container-Klasse fuer die Pokemon
 * Orientierung am Singletonmuster
 */
public class PokemonContainer implements Iterable<Pokemon> {

    /**
     * Beinhaltet, sofern vorhanden das einzig existierende PokemonContainer-Objekt
     */
    private static PokemonContainer unique = null;

    /**
     * Speicherung aller Pokemon in einer Liste
     */
    private ArrayList<Pokemon> pokemonList;

    /**
     * Speicherung aller geloeschter Pokemon anhand der ID
     */
    private ArrayList<Integer> deletedPokemonList;

    /**
     * Erstellung eines PokemonContainers, nur von eigener Klasse aufrufbar
     * Initialisierung der Pokemonliste
     */
    private PokemonContainer() {
        pokemonList = new ArrayList<>();
        deletedPokemonList = new ArrayList<>();
    }

    /**
     * Ueberpruefung, ob schon ein Objekt der PokemonContainer-Klasse existiert,
     * erstellen eines neuen Objekts, wenn bisher keines erstellt wurde
     * @return Einzig existierendes PokemonContainer-Objekt
     */
    public static PokemonContainer instance() {
        if (unique == null) {
            unique = new PokemonContainer();
        }
        return unique;
    }


    /**
     * Hinzufuegen eines Pokemon zur Pokemonliste
     * @param pokemon Pokemon, das hinzugefuegt werden soll
     * @throws AlreadyExistException Werfen einer Ausnahme, wenn das Pokemon, schon der Liste hinzugefuegt wurde
     */
    public void addPokemon(Pokemon pokemon) {
        if (!pokemonList.contains(pokemon)) {
            pokemonList.add(pokemon);
        } else {
            throw new AlreadyExistException("Pokemon was already created");
        }
    }

    /**
     * Rueckgabe des Indexes in der Pokemonliste eines Pokemon
     * @param pokemon Pokemon, von welchem man die ID wissen will
     * @return Index in der Pokemonliste, falls Pokemon in der Liste enthalten, sonst -1
     */
    public int getIndex(Pokemon pokemon) {
        return pokemonList.indexOf(pokemon);
    }

    /**
     * Rueckgabe der Laenge der Pokemonliste
     * @return Laenge der Pokemonliste
     */
    public int getLength() {
        return pokemonList.size();
    }

    /**
     * Rueckgabe des i-ten Pokemon in der Liste
     * @param i Index vom gewuenschten Pokemon
     * @return i-tes Pokemon in der Liste
     * throws IndexOutOfBoundsException freiwillge Ueberpruefung, ob ein Index mitgegeben wurde, der nicht in der Liste existiert
     */
    public Pokemon getPokemon(int i) {
        return pokemonList.get(i);
    }

    /**
     * Rueckgabe des Pokemon, mit einer uebergebenen ID
     * @param id ID, vom Pokemon das man erhalten will
     * @return Pokemon, mit uebergebener ID
     * @throws DoesntExistException Pokemon mit gewuenschter ID existiert nicht
     */
    public Pokemon getPokemonByID(int id) {
        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getID() == id) {
                return pokemon;
            }
        }
        throw new DoesntExistException("Pokemon with ID: " + id + " doesn't exist");
    }

    /**
     * Ersetzen des Pokemon mit einer neuen Version dieses, also ersetzen des Pokemon mit gleicher ID durch neues Pokemon
     * @param update Pokemon, welches erneuert werden soll
     */
    public void updatePokemon(Pokemon update) {
        if (getIndex(update) != -1) {
            pokemonList.set(getIndex(update), update);
        }
    }

    /**
     * Loeschen eines Pokemon mit uebergebener ID
     * @param id ID vom Pokemon, welches geloescht werden soll
     */
    public void deletePokemon(int id) {
        int index = getIndexByID(id);
        if (index < 0) {
            throw new DoesntExistException("ID doesn't exist");
        }
        pokemonList.remove(index);

        for (int i = index; i < pokemonList.size(); i++) {
            pokemonList.get(i).idDecrement();
        }
        Pokemon.counterDecrement();

        deletedPokemonList.add(id);
        deletedPokemonList.sort(Comparator.comparingInt(o -> o));
    }

    /**
     * Rueckgabe des Indexes in der Pokemonliste anhand der ID vom Pokemon
     * @param id ID des Pokemon, von dem man den Index haben will
     * @return Index in der Pokemonliste anhand der ID vom Pokemon
     */
    public int getIndexByID(int id) {
        for (int i = 0; i < pokemonList.size(); i++) {
            if (pokemonList.get(i).getID() == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Ueberschreiben der iterator-Methode der Iterable-Schnittstelle
     * Erlauben u.a. einer enhanced-for-Schleife durch die Pokemonliste
     * @return Ein Iterator-Objekt mit Pokemon
     */
    @Override
    public Iterator<Pokemon> iterator() {
        return pokemonList.iterator();
    }

    /**
     * Rueckabe der Liste der IDs von den Pokemon, die geloescht wurden
     * @return Liste der IDs von den Pokemon, die geloescht wurden
     */
    public ArrayList<Integer> getDeletedPokemonList() {
        return deletedPokemonList;
    }

}
