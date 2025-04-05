package Pokemon;

/**
 * Schnittstelle fuer zusaetzliche Zeichenkettenmethoden
 */
public interface CharSupport {

    /**
     * Zaehlen wie oft ein bestimmtes Zeichen in einer Zeichenkette vorkommt
     * @param s Zeichenkette, die abgezählt werden soll
     * @param c Zeichen, wovon die Haeufigkeit bestimmt werden soll
     * @return Haeufigkeit vom Zeichen c in Zeichenkette s
     */
    default int count (String s, char c) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

}
