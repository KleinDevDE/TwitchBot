package de.kleindev.twitchbot.websocket;

import de.kleindev.twitchbot.logging.LogType;
import de.kleindev.twitchbot.logging.Logger;
import de.kleindev.twitchbot.websocket.packets.errors.InvalidDataPacket;
import org.java_websocket.WebSocket;

public abstract class WebSocketEndpoint {
    public abstract void onMessage(WebSocket webSocket, Packet packet);
    public void sendInvalidDataPacket(WebSocket webSocket){
        Logger.log(LogType.DEBUG, "[WebSocket|"+this.getClass().getAnnotation(Endpoint.class).address()+"] ("+webSocket.getRemoteSocketAddress().getAddress().getHostAddress()+") Unknown packet received");
        Logger.log(LogType.DEBUG, "[WebSocket|"+this.getClass().getAnnotation(Endpoint.class).address()+"] ("+webSocket.getRemoteSocketAddress().getAddress().getHostAddress()+") Sending InvalidEndpointPacket");
        webSocket.send(new InvalidDataPacket().getSendableString());
    };
}
