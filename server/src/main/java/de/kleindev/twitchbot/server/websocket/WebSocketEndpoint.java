package de.kleindev.twitchbot.server.websocket;

import de.kleindev.twitchbot.server.logging.LogType;
import de.kleindev.twitchbot.server.logging.Logger;
import de.kleindev.twitchbot.server.websocket.packets.errors.InvalidDataPacket;
import org.java_websocket.WebSocket;

public abstract class WebSocketEndpoint {
    public abstract void onMessage(WebSocket webSocket, Packet packet);
    public void sendInvalidDataPacket(WebSocket webSocket){
        Logger.log(LogType.DEBUG, "[WebSocket|"+this.getClass().getAnnotation(Endpoint.class).address()+"] ("+webSocket.getRemoteSocketAddress().getAddress().getHostAddress()+") Unknown packet received");
        Logger.log(LogType.DEBUG, "[WebSocket|"+this.getClass().getAnnotation(Endpoint.class).address()+"] ("+webSocket.getRemoteSocketAddress().getAddress().getHostAddress()+") Sending InvalidEndpointPacket");
        webSocket.send(new InvalidDataPacket().getSendableString());
    };
}
