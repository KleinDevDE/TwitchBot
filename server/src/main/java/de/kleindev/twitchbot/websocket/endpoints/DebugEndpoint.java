package de.kleindev.twitchbot.websocket.endpoints;

import de.kleindev.twitchbot.websocket.Endpoint;
import de.kleindev.twitchbot.websocket.Packet;
import de.kleindev.twitchbot.websocket.WebSocketEndpoint;
import org.java_websocket.WebSocket;

@Endpoint(address = "debug")
public class DebugEndpoint extends WebSocketEndpoint {
    @Override
    public void onMessage(WebSocket webSocket, Packet packet) {

    }
}
