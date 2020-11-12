package old.websocket.endpoints;


import old.websocket.packets.Packet;
import org.java_websocket.WebSocket;

public abstract class WebSocketEndpoint {
    public abstract void onMessage(Packet packet, WebSocket webSocket);
}
