package Communicator;

import Converter.CsvConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

import Pokemon.Pokemon.Pokemon;
import Pokemon.Pokemon.PokemonContainer;

/**
 * Wird zur Kummunikation mit den PokemonCsv verwendet
 */
public class CsvCommunicator {

    /**
     * Die ArrayListe speichert die Zeilen der dazugehörigen Csv
     */
    private static ArrayList<String> germanDataLines = new ArrayList<>();
    /**
     * Die ArrayListe speichert die Zeilen der dazugehörigen Csv
     */
    private static ArrayList<String> englishDataLines = new ArrayList<>();
    /**
     * Die ArrayListe speichert die Zeilen der dazugehörigen Csv
     */
    private static ArrayList<String> japanDataLines = new ArrayList<>();

    /**
     * Speichern die Adresse der Csv
     */
    private static final String germanAdresse = System.getProperty("user.dir") + "/src/csv/pokemonDeutsch.csv";
    /**
     * Speichern die Adresse der Csv
     */
    private static final String englishAdresse = System.getProperty("user.dir") + "/src/csv/pokemonEnglisch.csv";
    /**
     * Speichern die Adresse der Csv
     */
    private static final String japaneseAdresse = System.getProperty("user.dir") + "/src/csv/pokemonJapan.csv";

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
     * Wird verwendet um eine Id zu suchen
     */
    public static final String notFound = "notFound";

    /**
     * Die Summe aller Lines der Pokemon Csvs
     */
    public static int allLines = 0;


    /**
     * Mit dieser Methode kann man die Arraylists wieder in die dazugehörige Csv speichern
     * @throws FileNotFoundException Datei nicht gefunden
     */
    public static void update_csv() throws FileNotFoundException {

        File f = new File(germanAdresse);
        try (PrintWriter pw = new PrintWriter(f)) {
            germanDataLines.forEach(pw::println);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("couldn't update the file");
        }

        f = new File(englishAdresse);
        try (PrintWriter pw = new PrintWriter(f)) {
            englishDataLines.forEach(pw::println);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("couldn't update the file");
        }

        f = new File(japaneseAdresse);
        try (PrintWriter pw = new PrintWriter(f)) {
            japanDataLines.forEach(pw::println);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("couldn't update the file");
        }
    }

    /**
     * Diese Methode entnimmt den Csvs alle Zeilen und speichert sie in den dazugehörigen ArrayLists
     * @throws Exception Konnte Pokemon nicht fetschen
     */
    public static void fetchPokemon() throws Exception {
        Path germanFile = Path.of(germanAdresse);
        Path englishFile = Path.of(englishAdresse);
        Path japaneseFile = Path.of(japaneseAdresse);

        //Files.lines() wird verwendet um alle Zeilen einer Csv in einem Stream zu speichern welcher
        //dan in einer Arraylioste gespeichert wird
        try (Stream<String> stream = Files.lines(germanFile, StandardCharsets.UTF_8)) {
            stream.forEach(s -> germanDataLines.add(s));
        } catch (IOException e) {
            throw new IOException("Konnte die Csv Datei nicht finden");
        } catch (Exception e) {
            throw new Exception("Das fetchen der Pokemon ist fehlgeschlagen");
        }

        try (Stream<String> stream = Files.lines(englishFile, StandardCharsets.UTF_8)) {
            stream.forEach(s -> englishDataLines.add(s));
        } catch (IOException e) {
            throw new IOException("Konnte die Csv Datei nicht finden");
        } catch (Exception e) {
            throw new Exception("Das fetchen der Pokemon ist fehlgeschlagen");
        }

        try (Stream<String> stream = Files.lines(japaneseFile, StandardCharsets.UTF_8)) {
            stream.forEach(s -> japanDataLines.add(s));
        } catch (IOException e) {
            throw new IOException("Konnte die Csv Datei nicht finden");
        } catch (Exception e) {
            throw new Exception("Das fetchen der Pokemon ist fehlgeschlagen");
        }

        //Diese Methode wird verwendet um die Zeilen aus der Csv in Pokemon umzuwandeln
        initializePokemon();
    }

    /**
     * Mit dieser Methode kann man den PokemonContainer wieder in Zeilen umwandeln die man dan fuer das Abspeichern in der
     * Csv braucht
     */
    public static void updateDataLines() {
        CsvConverter csv = new CsvConverter();
        PokemonContainer pc = PokemonContainer.instance();

        ArrayList<String> newGermanDataLines = new ArrayList<>();
        ArrayList<String> newEnglishDataLines = new ArrayList<>();
        ArrayList<String> newJapaneseDataLines = new ArrayList<>();

        for (int i = 0; i < pc.getLength(); i++) {
            Pokemon poke = pc.getPokemon(i);
            if(poke.isInformation(german)) {
                String pokemonString = csv.convertPokemonToCsvLine(poke, german);
                newGermanDataLines.add(pokemonString);
            }
            if(poke.isInformation(english)) {
                String pokemonString = csv.convertPokemonToCsvLine(poke, english);
                newEnglishDataLines.add(pokemonString);
            }
            if(poke.isInformation(japanese)) {
                String pokemonString = csv.convertPokemonToCsvLine(poke, japanese);
                newJapaneseDataLines.add(pokemonString);
            }
        }
        germanDataLines = newGermanDataLines;
        englishDataLines = newEnglishDataLines;
        japanDataLines = newJapaneseDataLines;
    }

    /**
     * Diese Methode wird verwendet um die Datenzeilen der Csv von den Zeilen in Pokemon umzuwandeln
     * @throws Exception Wenn das Initialisieren fehl schlaegt
     */
     private static void initializePokemon() throws Exception {
        CsvConverter csv = new CsvConverter();
        PokemonContainer pc = PokemonContainer.instance();
         allLines = germanDataLines.size() + englishDataLines.size() + japanDataLines.size();
         int controllSize = allLines;
        while (allLines > 0 && CsvConverter.general < controllSize * 5){
            Pokemon p = csv.convertCsvLinesToPokemon();
            if(p != null) {
                pc.addPokemon(p);
            }
        }
        if(CsvConverter.general == allLines * 5 ) {
            throw new Exception("konnte die letzte id nicht finden");
        }
        csv.resetgeneral();
    }

    /**
     * Mit dieser Methode kann man mit einer Id herrausfinden, ob die Strings der Sprachenarray einen String haben der die id beinhält
     * @param language Sprache
     * @param id Die Actuelle Id
     * @return Gibt einen Wert zurueck der verwendet wird um zu zeigen das es die id in der Sprache nicht gibt
     */
    public static String getLineWithIdFromLanguage(String language, int id) {
        String idString = "id:" + id;
        if(german.equals(language) && germanDataLines.stream().anyMatch(s -> s.contains(idString))) {
            return germanDataLines.stream().filter(s -> s.contains(idString)).toList().get(0);
        } else if(english.equals(language) && englishDataLines.stream().anyMatch(s -> s.contains(idString))) {
            return englishDataLines.stream().filter(s -> s.contains(idString)).toList().get(0);
        } else if (japanese.equals(language) && japanDataLines.stream().anyMatch(s -> s.contains(idString))) {
            return japanDataLines.stream().filter(s -> s.contains(idString)).toList().get(0);
        } else {
            return notFound;
        }
    }

}
