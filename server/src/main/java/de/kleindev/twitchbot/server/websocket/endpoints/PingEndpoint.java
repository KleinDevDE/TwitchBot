package de.kleindev.twitchbot.server.websocket.endpoints;

import de.kleindev.twitchbot.server.logging.LogType;
import de.kleindev.twitchbot.server.logging.Logger;
import de.kleindev.twitchbot.server.websocket.Endpoint;
import de.kleindev.twitchbot.server.websocket.Packet;
import de.kleindev.twitchbot.server.websocket.WebSocketEndpoint;
import de.kleindev.twitchbot.server.websocket.packets.ping.PingPacket;
import de.kleindev.twitchbot.server.websocket.packets.ping.PongPacket;
import org.java_websocket.WebSocket;

@Endpoint(address = "ping")
public class PingEndpoint extends WebSocketEndpoint {
    @Override
    public void onMessage(WebSocket webSocket, Packet packet) {
        if (packet instanceof PingPacket){
            Logger.log(LogType.DEBUG, "[WebSocket|ping] ("+webSocket.getRemoteSocketAddress().getAddress().getHostAddress()+") Ping received");
            Logger.log(LogType.DEBUG, "[WebSocket|ping] ("+webSocket.getRemoteSocketAddress().getAddress().getHostAddress()+") Sending pong..");
            webSocket.send(new PongPacket().getSendableString());
        } else if (packet instanceof PongPacket){
            Logger.log(LogType.DEBUG, "[WebSocket|ping] ("+webSocket.getRemoteSocketAddress().getAddress().getHostAddress()+") Pong received");
        } else {
            sendInvalidDataPacket(webSocket);
        }
    }
}
