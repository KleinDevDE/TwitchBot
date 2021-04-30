package de.kleindev.twitchbot.managers;

import com.google.inject.AbstractModule;
import de.mkammerer.argon2.Argon2;

public class ArgonManager extends AbstractModule {
    private static Argon2 argon2;

    public ArgonManager(Argon2 argon2){
        ArgonManager.argon2 = argon2;
    }

    public String hash(int iterations, String string){
        return argon2.hash(iterations, 65536, 1, string.toCharArray());
    }

    public boolean verify(String hashed, String string){
        return argon2.verify(hashed, string.toCharArray());
    }
}
