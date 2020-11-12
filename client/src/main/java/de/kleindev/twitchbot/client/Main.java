package de.kleindev.twitchbot.client;

import de.kleindev.twitchbot.client.websocket.WSClient;
import de.kleindev.twitchbot.client.websocket.WebSocketManager;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) throws Exception {
        WebSocketManager.registerClient(new WSClient("localhost", 8081, "ping"));
        WebSocketManager.registerClient(new WSClient("localhost", 8081, "auth"));
        Application.launch(JavaFXMain.class);
    }
}
