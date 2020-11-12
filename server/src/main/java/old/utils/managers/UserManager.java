package old.utils.managers;

import java.util.UUID;

public class UserManager {
    public static UUID login(String username, String password){
        return UUID.randomUUID();
        //TODO login
    }

    public static UUID register(String username, String email, String password){
        return null;
    }

    public static boolean usernameExists(String username){
        return false;
    }

    public static boolean emailExists(String username){
        return false;
    }
}
