package de.kleindev.twitchbot;

import de.kleindev.twitchbot.utils.ArgonManager;
import de.kleindev.twitchbot.websocket.WSClient;
import de.kleindev.twitchbot.websocket.WebSocketManager;
import de.mkammerer.argon2.Argon2Factory;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) throws Exception {
        new ArgonManager(Argon2Factory.create(15, 225));
        WebSocketManager.registerClient(new WSClient("localhost", 8081, "ping"));
        WebSocketManager.registerClient(new WSClient("localhost", 8081, "auth"));
        WebSocketManager.registerClient(new WSClient("localhost", 8081, "dev"));
        WebSocketManager.registerClient(new WSClient("localhost", 8081, "sync"));
        WebSocketManager.registerClient(new WSClient("localhost", 8081, "user"));
        Application.launch(JavaFXMain.class);
    }
}
