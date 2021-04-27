package de.kleindev.twitchbot.websocket.endpoints;

import de.kleindev.twitchbot.logging.Logger;
import de.kleindev.twitchbot.websocket.Endpoint;
import de.kleindev.twitchbot.websocket.Packet;
import de.kleindev.twitchbot.websocket.WebSocketEndpoint;
import de.kleindev.twitchbot.websocket.packets.ping.PingPacket;
import de.kleindev.twitchbot.websocket.packets.ping.PongPacket;
import org.java_websocket.WebSocket;

@Endpoint(address = "ping")
public class PingEndpoint extends WebSocketEndpoint {
    @Override
    public void onMessage(WebSocket webSocket, Packet packet) {
        if (packet instanceof PingPacket){
            Logger.trace( "[WebSocket|ping] ("+webSocket.getRemoteSocketAddress().getAddress().getHostAddress()+") Ping received");
            Logger.trace("[WebSocket|ping] ("+webSocket.getRemoteSocketAddress().getAddress().getHostAddress()+") Sending pong..");
            webSocket.send(new PongPacket().getSendableString());
            Logger.trace("[WebSocket|ping] ("+webSocket.getRemoteSocketAddress().getAddress().getHostAddress()+") pong sent!");
        } else if (packet instanceof PongPacket){
            Logger.trace("[WebSocket|ping] ("+webSocket.getRemoteSocketAddress().getAddress().getHostAddress()+") Pong received");
        } else {
            sendInvalidDataPacket(webSocket);
        }
    }
}
