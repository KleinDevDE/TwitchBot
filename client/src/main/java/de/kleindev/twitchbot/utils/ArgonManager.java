package de.kleindev.twitchbot.utils;

import de.kleindev.twitchbot.websocket.packets.auth.ArgonIterationPacket;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Helper;

public class ArgonManager {
    private static Argon2 argon2;
    public static int iterations;

    public ArgonManager(Argon2 argon2){
        ArgonManager.argon2 = argon2;
        System.out.println("Check the best iterations for argon2...");
        iterations = Argon2Helper.findIterations(argon2, 1000, 65536, 1);
        System.out.println("Result: " + iterations + " iterations");
        ArgonIterationPacket argonIterationPacket = new ArgonIterationPacket();
        argonIterationPacket.setIterations(iterations);
        argonIterationPacket.send("auth");
    }

    public static String hash(String string){
        return argon2.hash(iterations, 65536, 1, string.toCharArray());
    }

    public static boolean verify(String hashed, String string){
        return argon2.verify(hashed, string.toCharArray());
    }
}
