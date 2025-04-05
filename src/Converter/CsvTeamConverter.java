package Converter;

import User.User;

/**
 * Wandelt Strings zu PokemonID und andersrum um
 */
public class CsvTeamConverter {

    /**
     * Die Methode wandelt die Id von einem Pokemon in einen String fuer die Csv um
     * @param id Id des Pokemons
     * @return String fuer die Csv
     */
    public String convertPokemonToCsvLine(int id) {
            return "id:" + id + ',' + User.getCurrentUser().getName();
    }

    /**
     * Wandelt einen String aus einer Zeile der Csv in eine Id um welche im Teamcontainer gespeichert werden kann
     * @param dataLine Zeile der Csv
     * @return gibt -1 zurueck wenn die id, der User nicht gefunden wurde oder der String leer ist. Wenn der User und die Id vorhanden sind dan gitb er die Id des Pokemon der Datenlinie zurueck
     */
    public int convertTeamDataLineToPokemon(String dataLine) {
        if(dataLine.isEmpty()) {
            return -1;
        }
        boolean userFound = false;
        if (!dataLine.contains("id:")) {
            System.err.println("id in teams wurde verändert/oder ist falsch");
            return -1;
        }
        String userName = dataLine.substring(dataLine.indexOf(',') + 1);

        if (User.getCurrentUser().getName().equals(userName)) {
            userFound = true;
        }

        if(userFound) {
            int id;
            String idString = dataLine.substring(dataLine.indexOf(':') + 1, dataLine.indexOf(','));
            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("id konnte nicht umgewandelt werden");
            }
            return id;
        }
        return -1;
    }
}
