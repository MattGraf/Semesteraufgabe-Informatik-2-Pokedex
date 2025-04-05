package Converter;


import User.User;

/**
 * Wandelt Strings zu User und andersrum um
 */
public class UserCsvConverter {

    /**
     * Wandelt User in Strings um
     * @param u User der umgewandelt werden soll
     * @return einen String der Die Userinfromationen speichert
     */
    public String convertUserToCsvLine(User u) {
        return  u.getName() + "," + u.getPassword();
    }

    /**
     * Wandelt einen String in einen User um
     * @param s String der zu User umgewandelt
     * @return Gibt einen User zurueck
     */
    public User convertCsvLineToUser(String s) {
        String name = s.substring(0, s.indexOf(','));
        String passwort = s.substring(s.indexOf(',') + 1);
        return new User(name, passwort);
    }
}
