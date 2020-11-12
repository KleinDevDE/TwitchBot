package old.websocket.endpoints;

import old.websocket.packets.Packet;
import org.java_websocket.WebSocket;

@ServerEndpoint(endpoint = "command")
public class CommandEndpoint extends WebSocketEndpoint {
    @Override
    public void onMessage(Packet packet, WebSocket webSocket) {

    }
}
