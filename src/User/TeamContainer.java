package User;

/**
 * Container-Klasse fuer das Team
 * Orientierung am Singletonmuster
 */
public class TeamContainer {

    /**
     * Beinhaltet, sofern vorhanden das einzig existierende TeamContainer-Objekt
     */
    private static TeamContainer unique = null;

    /**
     * Speicherung des Teams mittels der ID von den Pokemon
     */
    private int[] team;

    /**
     * Erstellung eines TeamContainers, nur von eigener Klasse aufrufbar
     * Initialisierung des Teams und Festlegung auf 6 Pokemon pro Team
     */
    private TeamContainer() {
        team = new int[6];
        for (int i = 0; i < 6; i++) {
            team[i] = -1;
        }
    }

    /**
     * Ueberpruefung, ob schon ein Objekt der TeamContainer-Klasse existiert,
     * erstellen eines neuen Objekts, wenn bisher keines erstellt wurde
     * @return Einzig existierendes TeamContainer-Objekt
     */
    public static TeamContainer instance() {
        if (unique == null) {
            unique = new TeamContainer();
        }
        return unique;
    }

    /**
     * Rueckgabe des ganzen Teams
     * @return Team-Array
     */
    public int[] getTeam() {
        return team;
    }

    /**
     * Ueberpruefung, ob ein Pokemon mit einer bestimmten ID im Team ist
     * @param id ID des Pokemon, auf das man ueberpruefen will, obs im Team ist
     * @return Ob das Pokemon mit der uebergebenen ID im Team ist
     */
    public boolean checkPokemon(int id) {
        for (int i = 0; i < 6; i++) {
            if (team[i] == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Rueckgabe der ID des i-ten Pokemon in der Liste
     * @param index Index im Team-Array
     * @return ID des i-ten Pokemon in der Liste
     * @throws IndexOutOfBoundsException Mitgabe eines Indexes, der nicht im Array enthalten ist
     */
    public int getPokemon(int index) {
        if (index < 0 || index > 5) {
            throw new IndexOutOfBoundsException("Team-Index out of bounds");
        }
        return team[index];
    }

    /**
     * Hinzufuegen eines Pokemon an die erste unbesetzte Stelle im Team
     * kein Hinzufuegen, wenn alle Plaetze belegt sind
     * @param pokemonID ID des Pokemon, das man dem Team hinzufuegen soll
     * @return Erfolgreiches Hinzufuegen
     */
    public boolean setPokemon(int pokemonID) {
        for (int i = 0; i < 6; i++) {
            if (team[i] < 0) {
                team[i] = pokemonID;
                return true;
            }
        }
        return false;
    }

    /**
     * Loeschen des Pokemon an i-ter Stelle im Team
     * @param index Index vom Pokemon im Team, das geloescht werden soll
     * @throws IndexOutOfBoundsException Mitgabe eines Indexes, der nicht im Array enthalten ist
     */
    public void deletePokemon(int index) {
        if (index < 0 || index > 5) {
            throw new IndexOutOfBoundsException("Teamindex doesn't exist");
        }
        team[index] = -1;
        for (int i = index; i < 5; i++) {
            team[i] = team[i + 1];
        }
        team[5] = -1;
    }

    /**
     * Loeschen des Pokemon mit einer uebergebenen ID
     * @param pokemonID ID vom Pokemon, das geloescht werden soll
     */
    public void deletePokemonByID(int pokemonID) {
        for (int i = 0; i < 6; i++) {
            if (team[i] == pokemonID) {
                deletePokemon(i);
            }
        }
    }

}
