package de.kleindev.twitchbot.server;

import de.kleindev.twitchbot.server.logging.Logger;
import de.kleindev.twitchbot.server.websocket.EndpointManager;
import de.kleindev.twitchbot.server.websocket.WebSocketManager;
import de.kleindev.twitchbot.server.websocket.endpoints.AuthEndpoint;
import de.kleindev.twitchbot.server.websocket.endpoints.PingEndpoint;
import de.kleindev.twitchbot.server.websocket.exceptions.InvalidWebSocketEndpointClassException;

import java.io.File;

public class Main {
    public static void main(String[] args) throws InvalidWebSocketEndpointClassException {
        new Logger(new File("../logs"));
        EndpointManager.registerEndpoint(new PingEndpoint());
        EndpointManager.registerEndpoint(new AuthEndpoint());
        WebSocketManager.startServer("127.0.0.1", 8081);
    }
}
