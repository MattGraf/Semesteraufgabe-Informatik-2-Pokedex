package Communicator;

import Converter.UserCsvConverter;
import User.UserContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Wird zur Kummunikation mit den UserCsv verwendet
 */
public class UserCsvCommunicator {
    /**
     * ArrayListe, die die Zeilen der Csv abspeichert
     */
    public static ArrayList<String> dataLines = new ArrayList<>();

    /**
     * Standartsprache ist Deutsch
     */
    private static final String userAdresse  = System.getProperty("user.dir") + "/src/Csv/user.csv";

    /**
     * Diese Methoden kann die Arraylist aus String anpassen die in die Csv gespeichert wird
     * @throws FileNotFoundException Wird geworfen wenn die File nicht gefunden wird
     */
    public static void update_csv() throws FileNotFoundException {
        File f = new File(userAdresse);
        try (PrintWriter pw = new PrintWriter(f)) {
            dataLines.forEach(pw::println);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("couldn't update the file");
        }
    }


    /**
     * Methode wird die User aus der Csv raushohlen und die dataLines gespeichert
     * @throws IOException Falls die File nicht gefunden wird
     */
    public static void fetchUser() throws IOException {
        Path filePath = Path.of(userAdresse);

        try (Stream<String> stream = Files.lines(filePath, StandardCharsets.UTF_8)) {
            stream.forEach(s -> dataLines.add(s));
            initializeUser();
        } catch (IOException e) {
            throw new IOException("couldn't fetch user");
        } catch (Exception e) {
            throw new RuntimeException("initilize failed");
        }

    }


    /**
     * Diese Methoden kann die Arraylist aus String anpassen die in die Csv gespeichert wird
     */
    public static void updateDataLines() {
        UserCsvConverter csv = new UserCsvConverter();
        UserContainer userContainer = UserContainer.instance();
        ArrayList<String> newDataLines = new ArrayList<>();
        for (int i = 0; i < userContainer.getUsersArraySize(); i++) {
            String userString = csv.convertUserToCsvLine(userContainer.getUsersAtInt(i));
            newDataLines.add(userString);
        }
        dataLines = newDataLines;
    }


    /**
     * Wandelt die Datalines in User um
     */
     private static void initializeUser() {
        UserCsvConverter csv = new UserCsvConverter();
        UserContainer userContainer = UserContainer.instance();
        if (dataLines.size() > 0) {
           for (int i = 0; i < dataLines.size(); i++) {
               userContainer.addUser(csv.convertCsvLineToUser(dataLines.get(i)));
           }
        }
    }
}
