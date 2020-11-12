package de.kleindev.twitchbot.server.websocket.endpoints;

import de.kleindev.twitchbot.server.websocket.Endpoint;
import de.kleindev.twitchbot.server.websocket.Packet;
import de.kleindev.twitchbot.server.websocket.WebSocketEndpoint;
import de.kleindev.twitchbot.server.websocket.packets.errors.ErrorReportPacket;
import org.java_websocket.WebSocket;

@Endpoint(address = "dev")
public class DeveloperEndpoint extends WebSocketEndpoint {
    @Override
    public void onMessage(WebSocket webSocket, Packet packet) {
        if (packet instanceof ErrorReportPacket){
            //TODO Send E-Mail, Discord or post automatic issue on Github or something else
        }
    }
}
