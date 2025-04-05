package Communicator;

import Pokemon.Pokemon.Pokemon;
import Pokemon.Pokemon.PokemonContainer;
import Converter.ImportCsvConverter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;


/**
 * Wird zur Kummunikation mit den ImportCsv verwendet
 */
public class CsvImporter {

    /**
     *Werden verwendet um die Zeilen aus einer csv zu speichern
     */
    private static ArrayList<String> dataLines = new ArrayList<>();

    /**
     * Strings um Sprachen zu im Code zu aendern
     */
    public static final String german = Pokemon.GERMAN;
    /**
     * Strings um Sprachen zu im Code zu aendern
     */
    public static final String english = Pokemon.ENGLISH;
    /**
     * Strings um Sprachen zu im Code zu aendern
     */
    public static final String japanese = Pokemon.JAPANESE;



    /**
     * Diese Methode entnimmt den Csv alle Zeilen und speichert sie in den ArrayLists
     * @param adresse Die adresse der File
     * @param language Die Sprache des Pokemons
     * @throws Exception wenn die file oder das einspeichern nicht funtioniert
     */
    public static void fetchImportedPokemon(String adresse, String language) throws Exception {
        try {
            Path germanFile = Path.of(adresse);
            //Files.lines Methode gibt einen Stream von allen Zeilen in der Csv zurueck
            try (Stream<String> stream = Files.lines(germanFile, StandardCharsets.UTF_8)) {
                stream.forEach(s -> dataLines.add(s));
                //Diese Methode wird verwendet um die Zeilen aus der Csv in Pokemon umzuwandeln
                initializePokemon(language);
            } catch (IOException e) {
                throw new IOException("couldn't fetch pokemon");
            } catch (Exception e) {
                throw new RuntimeException("initilize failed");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("pfad der Datei konnte nicht umgewandelt werden");
        }

    }

     /**
     * wird verwendet um die Datenzeilen der Csv von den Zeilen in Pokemon umzuwandeln
      * @param language Sprache des Pokemons
     */
     private static void initializePokemon(String language) throws Exception {
        ImportCsvConverter csv = new ImportCsvConverter();
        PokemonContainer pc = PokemonContainer.instance();
        for(int i = 0; i < dataLines.size(); i++) {
            Pokemon p = csv.convertCsvLinesToPokemon(dataLines.get(i), language);
            if(p != null) {
                pc.addPokemon(p);
            }
        }
        ArrayList<String> newDataLines = new ArrayList<>();
        dataLines = newDataLines;
    }

}
