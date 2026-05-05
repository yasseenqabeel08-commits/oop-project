import java.util.HashMap;
import java.util.Map;

public class UserService {

    private static Map<String, String> users = new HashMap<>();

    static {
        // default users
        users.put("Admin_1", "Admin@0001");
        users.put("Guest_1", "1234");
    }

    public static boolean login(String username, String password) {
        return users.containsKey(username) &&
                users.get(username).equals(password);
    }

    public static boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, password);
        return true;
    }
}