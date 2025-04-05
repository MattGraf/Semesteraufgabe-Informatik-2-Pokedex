package Pokemon;

/**
 * Eigene Exception-Klasse zur Erkennung schon enthaltenen Elementen in einem Container
 */
public class AlreadyExistException extends RuntimeException{

    /**
     * Uebergabe einer gewuenschten Nachricht an RuntimeException
     * @param message gewuenschte Nachricht
     */
    public AlreadyExistException(String message) {
        super(message);
    }

}
