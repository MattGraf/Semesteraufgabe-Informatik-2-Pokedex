package User;

/**
 * Eigene Exception-Klasse fuer Fehlerfaelle bei den Usern
 */
public class UserException extends RuntimeException {

    /**
     * Uebergabe einer gewuenschten Nachricht an RuntimeException
     * @param message gewuenschte Nachricht
     */
    public UserException(String message) {
        super(message);
    }
}
