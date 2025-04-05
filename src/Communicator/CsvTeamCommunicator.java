package Communicator;

import User.TeamContainer;
import User.User;

import Converter.CsvTeamConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Wird zur Kummunikation mit den TeamCsv verwendet
 */
public class CsvTeamCommunicator {

    /**
     * Eine Arrayliste, die die Zeilen einer Csv abspeichern
     */
    private static ArrayList<String> teamsDataLines = new ArrayList<>();

    /**
     * Adresse der TeamCsv
     */
    private static final String teamsAdresse = System.getProperty("user.dir") + "/src/Csv/teamPokemon.csv";


    /**
     * Mit dieser Methode kann man die Arraylists wieder in die dazugehörige Csv speichern
     * @throws FileNotFoundException Falls die File nicht gefunden wird
     */
    public static void update_csv() throws FileNotFoundException {
        File f = new File(teamsAdresse);

        try (PrintWriter pw = new PrintWriter(f)) {
            teamsDataLines.forEach(pw::println);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("couldn't update the file");
        }
    }

    /**
     * Diese Methode entnimmt den Csvs alle Zeilen und speichert sie in den dazugehoerigen ArrayLists. Wandelt die Strings in Die PokemonId ums
     * @throws Exception Falls die File nicht gefunden wird oder die initialisierung nicht funrioniert
     */
    public static void fetchPokemon() throws Exception {
        Path germanFile = Path.of(teamsAdresse);

        try (Stream<String> stream = Files.lines(germanFile, StandardCharsets.UTF_8)) {
            stream.forEach(s -> teamsDataLines.add(s));
        } catch (IOException e) {
            throw new IOException("couldn't fetch pokemon");
        } catch (Exception e) {
            throw new RuntimeException("initilize failed");
        }
        initializePokemon();
    }

    /**
     * Mit dieser Methode kann man den TeamsContainer wieder in Zeilen umwandeln die man dan fuer das Abspeichern in der
     * Csv braucht
     */
    public static void updateDataLines() {
        CsvTeamConverter csv = new CsvTeamConverter();
        TeamContainer tc = TeamContainer.instance();

        //Loescht alle Pokemon die einem User gehoehrt
        int size = teamsDataLines.size();
        for (int i = size - 1; i >= 0; i--) {
            if(teamsDataLines.get(i).contains(User.getCurrentUser().getName())) {
                teamsDataLines.remove(i);
            }
        }


        //Loescht alle Pokemon die einem User gehoehrt
        for (int i = 0; i < tc.getTeam().length; i++) {
            System.out.println(tc.getTeam()[i]);
            if(tc.getTeam()[i] != -1) {
                teamsDataLines.add(csv.convertPokemonToCsvLine(tc.getTeam()[i]));
            }
        }
    }

    /**
     * Wandelt die Strings aus der Csv in PokemonIds um und speichert sie in dem TeamContainer
     */
    private static void initializePokemon() {
        CsvTeamConverter csv = new CsvTeamConverter();
        TeamContainer tc = TeamContainer.instance();
        if(teamsDataLines.size() > 0 && !teamsDataLines.get(0).isEmpty()) {
            for(int i = 0; i < teamsDataLines.size(); i++) {
                int pokemonId = csv.convertTeamDataLineToPokemon(teamsDataLines.get(i));
                if(pokemonId != -1) {
                    tc.setPokemon(pokemonId);
                }
            }
        }
    }
}
