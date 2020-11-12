package old.websocket.packets.general;


import old.websocket.endpoints.ServerEndpoint;
import old.websocket.endpoints.WebSocketEndpoint;
import old.websocket.packets.Packet;

public class WrongEndpointPacket extends Packet {
    private String endpoint;

    public WrongEndpointPacket(WebSocketEndpoint endpoint){
        setEndpoint(endpoint);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setEndpoint(WebSocketEndpoint endpoint){
        this.endpoint = endpoint.getClass().getAnnotation(ServerEndpoint.class).endpoint();
    }
}
