package Pokemon;

/**
 * RuntimeException bei versuchter Rueckgabe eines nichtexistierenden Objekts
 */
public class DoesntExistException extends RuntimeException{

    /**
     * Uebergabe einer gewuenschten Nachricht an RuntimeException
     * @param message gewuenschte Nachricht
     */
    public DoesntExistException(String message) {
        super(message);
    }
}
