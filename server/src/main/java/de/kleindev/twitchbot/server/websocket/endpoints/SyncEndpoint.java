package de.kleindev.twitchbot.server.websocket.endpoints;

import de.kleindev.twitchbot.server.websocket.Endpoint;
import de.kleindev.twitchbot.server.websocket.Packet;
import de.kleindev.twitchbot.server.websocket.WebSocketEndpoint;
import org.java_websocket.WebSocket;

@Endpoint(address = "sync")
public class SyncEndpoint extends WebSocketEndpoint {
    @Override
    public void onMessage(WebSocket webSocket, Packet packet) {
        //TODO Make sure this connection will be active while the client application is online
        //  This connection will be used for status updates or something other things which needs to be sent to the client
    }
}
