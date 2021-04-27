package de.kleindev.twitchbot.websocket.endpoints;

import de.kleindev.twitchbot.websocket.Endpoint;
import de.kleindev.twitchbot.websocket.Packet;
import de.kleindev.twitchbot.websocket.WebSocketEndpoint;
import org.java_websocket.WebSocket;

@Endpoint(address = "sync")
public class SyncEndpoint extends WebSocketEndpoint {
    @Override
    public void onMessage(WebSocket webSocket, Packet packet) {
        //TODO Make sure this connection will be active while the client application is online
        //  This endpoint is used to sync all settings made by the client
        //  Also it will
    }
}
