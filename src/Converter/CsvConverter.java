package Converter;


import Communicator.CsvCommunicator;
import Pokemon.Pokemon.Information;
import Pokemon.Pokemon.Pokemon;
import Pokemon.Type.EnglishTypeContainer;
import Pokemon.Type.GermanTypeContainer;
import Pokemon.Type.JapaneseTypeContainer;
import Pokemon.Type.PokemonType;
import Pokemon.DoesntExistException;

import static Communicator.CsvCommunicator.*;

/**
 * Wandelt Strings zu Pokemon und andersrum um
 */
public class CsvConverter {
    /**
     * Die erste Id
     */
    public static int general = 1;
    /**
     * Der String der bearebitet werden soll
     */
    private String currentCsvLine;

    /**
     * Strings um Sprachen zu im Code zu aendern
     */
    private final String ENGLISH = Pokemon.ENGLISH;
    /**
     * Strings um Sprachen zu im Code zu aendern
     */
    private final String GERMAN = Pokemon.GERMAN;
    /**
     * Strings um Sprachen zu im Code zu aendern
     */
    private final String JAPANESE = Pokemon.JAPANESE;

    /**
     * Wandelt Pokemon in String um
     * @param p Pokemon
     * @param language Sprache fuer die Information
     * @return Gibt einen String zurueck. Falls die Sprache nicht passend ist dan gibt es null zurueck.
     */
    //id:1,pika,type=Water,2
    public String convertPokemonToCsvLine(Pokemon p, String language) {
        if (language.equals(GERMAN) || language.equals(ENGLISH) || language.equals(JAPANESE)) {
            return "id:" + p.getID() + "," + p.getInformation(language).getName() + "," + makeTypString(p.getInformation(language)) + p.getEvolution();
        } else {
            System.out.println("falsche sprache");
        }
        return null;
    }

    /**
     * Erstellt einen String der die Typen speichert
     * @param inf Die Information des Pokemons
     * @return String der Informationen
     */
    public String makeTypString(Information inf) {
        StringBuilder sb = new StringBuilder();
        if (inf.hasFirstType()) {
            sb.append("type=").append(inf.getFirstType().getName()).append(",");
        }
        if (inf.hasSecondType()) {
            sb.append("type=").append(inf.getSecondType().getName()).append(",");
        }
        return sb.toString();
    }

    /**
     * Setzt die erste Id zurueck
     */
    public void resetgeneral() {
        general = 0;
    }

    /**
     * Eine Methode die einen String mit der id (general), mit allen Sprachen aus allen Csv, erstellt
     * @return Gibt ein Pokemon zurueck
     * @throws Exception Falls der String currentCsvLine nicht umgewandelt werden kann
     */
    public Pokemon convertCsvLinesToPokemon() throws Exception {
        boolean firstInformationFound = false;
        int id = general;

        Pokemon p = setFirstInformtion(id, ENGLISH);
        if(p != null) {
            firstInformationFound = true;
            p = addOtherPokemonInformation(p, GERMAN, id);
            p = addOtherPokemonInformation(p, JAPANESE, id);
        }

        if(!firstInformationFound) {
            p = setFirstInformtion(id, GERMAN);
            if(p != null) {
                firstInformationFound = true;
                p = addOtherPokemonInformation(p, JAPANESE, id);
            }
        }

        if(!firstInformationFound) {
            p = setFirstInformtion(id, JAPANESE);
        }
        general++;
        return p;
    }

    /**
     * Fuegt einen Pokemon noch eine information fuer eine Sprache hinzu
     * @param p Pokemon
     * @param language Sprache im Code
     * @param id Die actuelle
     * @return Ein Pokemon
     * @throws Exception Falls die Umwandlung zum Pokemon nicht funtionieren
     */
    public Pokemon addOtherPokemonInformation(Pokemon p, String language, int id) throws Exception {
        if(!CsvCommunicator.getLineWithIdFromLanguage(language, id).equals(notFound)) {
            currentCsvLine = CsvCommunicator.getLineWithIdFromLanguage(language, id);

            cutCsvLineVariable();
            String name = cutCsvLineVariableFree();
            if(name.isEmpty()) {
                throw new IllegalArgumentException("Name konnte nicht erstellt werden! id =" + id);
            }
            cutCsvLineVariable();

            String typeString = cutCsvLineVariableFree();
            PokemonType type = null;
            if(typeString.contains("type=")) {
                typeString = typeString.substring(typeString.indexOf('=') + 1);
                type = isTypeValid(language, typeString, id);
            } else {
                throw new IllegalArgumentException("Type konnte nicht erstellt werden! id =" + id);
            }

            cutCsvLineVariable();

            Information information = null;

            String maybeTypeOrIcon = cutCsvLineVariableFree();
            if(maybeTypeOrIcon.contains("type=")) {
                maybeTypeOrIcon = maybeTypeOrIcon.substring(maybeTypeOrIcon.indexOf('=') + 1);
                PokemonType type2 = isTypeValid(language, maybeTypeOrIcon, id);
                information = new Information(p.getID(), name, type, type2, language);
                p.setInformation(information, language);
            } else {
                information = new Information(p.getID(), name, type, language);
                p.setInformation(information, language);
            }

            p.setInformation(information, language);
            CsvCommunicator.allLines--;
        }
        return p;
    }

    /**
     * Kuckt ob ein String ein Type fuer eine Sprache sein.
     * @param language Sprache des Pokemons
     * @param typeName String des Types
     * @param id Id des Pokemons
     * @return Gibt eine Typen zurueck
     * @throws DoesntExistException PokemonType in der jeweiligen Sprache nicht verfuegbar
     * @throws IllegalArgumentException Keine existierende Sprache mitgegeben
     */
    public PokemonType isTypeValid(String language, String typeName, int id) throws DoesntExistException {
        PokemonType pokemonType = null;
        if(language.equals(ENGLISH)) {
            EnglishTypeContainer tc = EnglishTypeContainer.instance();
            try {
                pokemonType = tc.getPokemonType(typeName);
            } catch (DoesntExistException e) {
                throw new DoesntExistException("Pokemon Type ist falsch mit der id: " + id);
            }
        } else if(language.equals(GERMAN)) {
            GermanTypeContainer gt = GermanTypeContainer.instance();
            try {
                pokemonType = gt.getPokemonType(typeName);
            } catch (DoesntExistException e) {
                throw new DoesntExistException("Pokemon Type ist falsch mit der id: " + id);
            }
        } else if(language.equals(JAPANESE)) {
            JapaneseTypeContainer jc = JapaneseTypeContainer.instance();
            try {
                pokemonType = jc.getPokemonType(typeName);
            } catch (DoesntExistException e) {
                throw new DoesntExistException("Pokemon Type ist falsch mit der id: " + id);
            }
        } else {
            throw new IllegalArgumentException("Sprache ist falsch");
        }
        return pokemonType;
    }

    /**
     * Erstellt ein Pokemon und befuellt die Informationen
     * @param id Actuelle Id des Pokemons
     * @param language Sprache des Pokemons
     * @return Ein Pokemon
     * @throws Exception Falls die erste Information nicht gesetzt werden kann
     */
    private Pokemon setFirstInformtion(int id, String language) throws Exception {
        if(!CsvCommunicator.getLineWithIdFromLanguage(language, id).equals(notFound)) {
            Pokemon p = new Pokemon();
            currentCsvLine = CsvCommunicator.getLineWithIdFromLanguage(language, id);

            //create the name
            cutCsvLineVariable();
            String name = cutCsvLineVariableFree();
            if(name.isEmpty()) {
                throw new IllegalArgumentException("Name konnte nicht erstellt werden! id =" + id + ", language " + language);
            }
            cutCsvLineVariable();

            String typeString = cutCsvLineVariableFree();
            PokemonType type = null;
            if(typeString.contains("type=")) {
                typeString = typeString.substring(typeString.indexOf('=') + 1);
                type = isTypeValid(language, typeString, id);
            } else {
                throw new IllegalArgumentException("Type konnte nicht erstellt werden! id = " + id + ", language " + language + ", String: " + typeString);
            }

            cutCsvLineVariable();

            Information information = null;

            String maybeTypeOrIcon = cutCsvLineVariableFree();
            if(maybeTypeOrIcon.contains("type=")) {
                maybeTypeOrIcon = maybeTypeOrIcon.substring(maybeTypeOrIcon.indexOf('=') + 1);
                PokemonType type2 = isTypeValid(language, maybeTypeOrIcon, id);;
                information = new Information(p.getID(), name, type, type2, language);
                p.setInformation(information, language);
            } else {
                information = new Information(p.getID(), name, type, language);
                p.setInformation(information, language);
            }

            if(!p.isInformation(language)) {
                throw new IllegalArgumentException("information konnte nicht erstellt werden! id =" + id + ", language " + language);
            }

            cutCsvLineVariable();
            cutCsvLineVariable();

            try {
                int evolution = Integer.parseInt(cutCsvLineVariableFree());
                p.setEvolution(evolution);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("evolution konnte nicht erstellt werden! id = " + id + ", language " + language);
            }
            cutCsvLineVariable();


            String mainIconString = System.getProperty("user.dir") + "/src/images/main_icons/" + String.format("%04d", p.getID()) + ".png";
            String mainPixelString = System.getProperty("user.dir") + "/src/images/pixel_icons/" + String.format("%04d", p.getID()) + ".png";

            p.setMainIcon(mainIconString);

            if(!p.setPixelIcon(mainPixelString)) {
                p.setPixelIcon(System.getProperty("user.dir") + "/src/UserInterface/res/pokemon/missing.png");
            }

            CsvCommunicator.allLines--;
            return p;
        } else {
            return null;
        }
    }

    /**
     * Schneidet alles bis zum Komma ab oder falls kein Komma vorhanden, gibt einen unbearbeiteten String zurueck
     * @return Gibt einen bearbeiteten String zurueck
     */
    public String cutCsvLineVariableFree() {
        if (currentCsvLine.indexOf(',') != -1) {
            return currentCsvLine.substring(0, currentCsvLine.indexOf(','));
        } else {
            return currentCsvLine;
        }
    }

    /**
     * Die Methode schneidet alles bis zum Komma ab. Da die CsvDataline Variablen speichern, kann man eine Variable wegschneiden.
     */
    public void cutCsvLineVariable() {
        currentCsvLine = currentCsvLine.substring(currentCsvLine.indexOf(',') + 1);
    }

}
