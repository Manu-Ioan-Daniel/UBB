package utils;

import java.util.HashSet;
import java.util.Set;

public class UserLogins {
    private static final Set<String> loggedInUsers = new HashSet<>();
    public static Set<String> getLoggedInUsers() {
        return loggedInUsers;
    }
}
