package User;

import Pokemon.AlreadyExistException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Container-Klasse fuer die User
 * Orientierung am Singletonmuster
 */
public class UserContainer implements  Iterable<User>{

    /**
     * Beinhaltet, sofern vorhanden das einzig existierende UserContainer-Objekt
     */
    private static UserContainer unique = null;

    /**
     * Speicherung aller Pokemon in einer Liste
     */
    private ArrayList<User> users;

    /**
     * Erstellung eines UserContainers, nur von eigener Klasse aufrufbar
     * Initialisierung der Userliste
     */
    private UserContainer() {
        users = new ArrayList<>();
    }

    /**
     * Ueberpruefung, ob schon ein Objekt der UserContainer-Klasse existiert,
     * erstellen eines neuen Objekts, wenn bisher keines erstellt wurde
     * @return Einzig existierendes UserContainer-Objekt
     */
    public static UserContainer instance() {
        if (unique == null) {
            unique = new UserContainer();
        }
        return unique;
    }

    /**
     * Rueckgabe der gesamten Liste der User
     * @return gesamte Liste der User
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Rueckgabe des i-ten Users in der Liste
     * @param index Index vom erwuenschten User in der Liste
     * @return User am mitgegebenen Index
     * @throws IndexOutOfBoundsException Index nicht in Liste enthalten
     */
    public User getUsersAtInt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= getUsersArraySize()) {
            throw new IndexOutOfBoundsException("Index in User-Array not available");
        }
        return users.get(index);
    }

    /**
     * Rueckgabe der Laenge der Userliste
     * @return Laenge der Userliste
     */
    public int getUsersArraySize() {
        return users.size();
    }

    /**
     * Hinzufuegen eines Users zur Userliste
     * @param user User, der hinzugefuegt wird
     * @throws AlreadyExistException Versuch ein User hinzuzufuegen, das schon existiert
     */
    public void addUser(User user) throws AlreadyExistException {
        List<User> list = users.stream()
                .filter(e -> e.equals(user))
                .toList();
        if (list.size() != 0) {
            throw new AlreadyExistException("User already exist");
        }
        users.add(user);
    }


    /**
     * Ueberschreiben der iterator-Methode der Iterable-Schnittstelle
     * Erlauben u.a. einer enhanced-for-Schleife durch die Userliste
     * @return Ein Iterator-Objekt mit Usern
     */
    @Override
    public Iterator<User> iterator() {
        return users.iterator();
    }
}
