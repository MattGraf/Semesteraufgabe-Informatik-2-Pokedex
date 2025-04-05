package Converter;


import Communicator.CsvCommunicator;
import Pokemon.Pokemon.Information;
import Pokemon.Pokemon.Pokemon;
import Pokemon.Type.EnglishTypeContainer;
import Pokemon.Type.GermanTypeContainer;
import Pokemon.Type.JapaneseTypeContainer;
import Pokemon.Type.PokemonType;
import Pokemon.DoesntExistException;



/**
 * Wandelt Strings zu Pokemon um
 */
public class ImportCsvConverter {
    /**
     * Varaiblen um die Sprache im Code auzuwaehlen
     */
    private final String ENGLISH = Pokemon.ENGLISH;
    private final String GERMAN = Pokemon.GERMAN;
    private final String JAPANESE = Pokemon.JAPANESE;

    /**
     * Der String der Bearbeitet wird
     */
    private static String currentCsvLine;

    /**
     * Die Methode wandelt Strings in Pokemon um
     * @param s Die Zeile die verwendet wird um ein Pokemon zu machen
     * @param language Die Sprache der Pokemon
     * @return Gibt ein Pokemon zurueck
     * @throws Exception Gibt zurueck ob eine Zeile verwendbar war
     */
    public Pokemon convertCsvLinesToPokemon(String s, String language) throws Exception {
        if (language.equals(GERMAN) || language.equals(ENGLISH) || language.equals(JAPANESE)) {
            currentCsvLine = s;
            if(s.indexOf(',') == -1) {
                throw new IllegalArgumentException("Zeile in Import ist falsch " + s);
            }
            return createPokemon(language);
        } else {
            throw new Exception("Sprache war falsch");
        }
    }

    /**
     * Die Methode wandelt currentCsvLine in ein Pokemon um
     * @param language Sprache der Pokemon
     * @return Gibt ein Pokemon zurueck
     * @throws Exception Wenn das Pokemon nicht gefunden wird
     */
    //p.getInformation(language).getName() + "," + makeTypString(p.getInformation(language)) + p.getEvolution();
    private Pokemon createPokemon(String language) throws Exception {
        if(language.equals(ENGLISH) || language.equals(GERMAN) || language.equals(JAPANESE)) {
            Pokemon p = new Pokemon();

            //create the name
            String name = cutCsvLineVariableFree();
            if(name.isEmpty()) {
                throw new IllegalArgumentException("Name konnte nicht erstellt werden! id =" + ", language " + language);
            }
            cutCsvLineVariable();

            String typeString = cutCsvLineVariableFree();
            PokemonType type = null;
            if(typeString.contains("type=")) {
                typeString = typeString.substring(typeString.indexOf('=') + 1);
                type = isTypeValid(language, typeString);
            } else {
                throw new IllegalArgumentException("Type konnte nicht erstellt werden! id = " + ", language " + language + ", String: " + typeString);
            }

            cutCsvLineVariable();

            Information information = null;

            String maybeTypeOrId = cutCsvLineVariableFree();
            if(maybeTypeOrId.contains("type=")) {
                maybeTypeOrId = maybeTypeOrId.substring(maybeTypeOrId.indexOf('=') + 1);
                PokemonType type2 = isTypeValid(language, typeString);
                information = new Information(p.getID(), name, type, type2, language);
                p.setInformation(information, language);
                cutCsvLineVariable();
            } else {
                information = new Information(p.getID(), name, type, language);
                p.setInformation(information, language);
            }

            if(!p.isInformation(language)) {
                throw new IllegalArgumentException("information konnte nicht erstellt werden! id =" + ", language " + language);
            }

            try {
                int evolution = Integer.parseInt(cutCsvLineVariableFree());
                p.setEvolution(evolution);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("evolution konnte nicht erstellt werden! , language " + language);
            }
            cutCsvLineVariable();

            p.setPixelIcon(System.getProperty("user.dir") + "/src/UserInterface/res/pokemon/missing.png");

            CsvCommunicator.allLines--;
            return p;
        } else {
            throw new Exception("falsche Sprache");
        }
    }

    /**
     * Kuckt ob der String zu einem PokemonType umwandelbar ist und gibt ihn dann zurueck, falls nicht dann gibt sie Null zurueck
     * @param language Sprache der Pokemon
     * @param typeName String der potenziell ein PokemonType sein könnte
     * @return Gibt einen PokemonType oder falls typeName ein fehlerhafter String ist, Null zurueck
     * @throws Exception Falls der String typeName nicht umwandel bar ist
     */
    public PokemonType isTypeValid(String language, String typeName) throws Exception {
        PokemonType pokemonType = null;
        if(language.equals(ENGLISH)) {
            EnglishTypeContainer tc = EnglishTypeContainer.instance();
            try {
                pokemonType = tc.getPokemonType(typeName);
            } catch (DoesntExistException e) {
                throw new DoesntExistException("Pokemon Type ist falsch");
            }
        } else if(language.equals(GERMAN)) {
            GermanTypeContainer gt = GermanTypeContainer.instance();
            try {
                pokemonType = gt.getPokemonType(typeName);
            } catch (DoesntExistException e) {
                throw new DoesntExistException("Pokemon Type ist falsch");
            }
        } else if(language.equals(JAPANESE)) {
            JapaneseTypeContainer jc = JapaneseTypeContainer.instance();
            try {
                pokemonType = jc.getPokemonType(typeName);
            } catch (DoesntExistException e) {
                throw new DoesntExistException("Pokemon Type ist falsch");
            }
        } else {
            throw new IllegalArgumentException("Sprache ist falsch");
        }
        return pokemonType;
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
