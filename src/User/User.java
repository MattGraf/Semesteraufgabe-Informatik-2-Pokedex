package User;

import java.awt.Image;

/**
 * Speicherung eines Users
 */
public class User {

    /**
     * Speicherung des Users, der gerade eingeloggt ist
     */
    private static User currentUser;

    /**
     * Speicherung des (Anmelde-)Namens vom User
     */
    private String name;

    /**
     * Speicherung des Passwords zum Account fuer die Anmeldung
     */
    private String password;

    /**
     * Erstellung eines Users mit Name und Password
     * @param name Name des Users
     * @param password Password des Users
     * @throws UserException Ungueltiger Name oder ungueltiges Passwort
     */
    public User(String name, String password) throws UserException {
        setName(name);
        setPassword(password);
    }

    /**
     * Setzen des Namens vom User bestehend aus Buchstaben und Ziffern
     * @param name Name des Users
     * @throws UserException Enthalten anderer Zeichen ausser Buchstaben und Ziffern
     */
    public void setName(String name) throws UserException {
        if (!name.matches("[A-Za-z0-9]+")) {
            throw new UserException("Illegal Name");
        }
        this.name = name;
    }

    /**
     * Setzen des Passwortes, welches nicht 0 sein darf
     * @param password Passwort des Users
     * @throws UserException Mitgabe einer leeren Zeichenkette
     */
    public void setPassword(String password) throws UserException {
        if (password.length() == 0) {
            throw new UserException("Illegal Password");
        }
        this.password = password;
    }

    /**
     * Rueckgabe des Namens vom User
     * @return Namen des Users
     */
    public String getName() {
        return name;
    }

    /**
     * Rueckgabe des Passworts vom User
     * @return Passwort des Users
     */
    public String getPassword() {
        return password;
    }

    /**
     * Ueberschreibung der equals-Methode
     * Ueberpruefung der Gleichheit des eigenen Objekts mit einem anderen anhand des Namens
     * @param o Zu vergleichendes Objekt
     * @return Gleichheit vom uebergebenen mit eigenem Objekt
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        User u = (User) o;
        return u.getName().equals(this.getName());
    }

    /**
     * Setzen des momentan eingeloggten User
     * @param user User, der momentan eingeloggt ist
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Rueckgabe des eingeloggten Users
     * @return User, der momentan eingeloggten ist
     */
    public static User getCurrentUser() {
        return currentUser;
    }
}
