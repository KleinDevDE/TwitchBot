package old;

import old.websocket.endpoints.LoginEndpoint;
import old.websocket.endpoints.UpdateEndpoint;
import old.utils.exceptions.InvalidWebSocketEndpointClassException;
import old.utils.managers.EndpointManager;
import old.utils.managers.WebSocketManager;

public class Main {
    public static void main(String[] args) throws InvalidWebSocketEndpointClassException {
        EndpointManager.registerEndpoint(new UpdateEndpoint());
        EndpointManager.registerEndpoint(new LoginEndpoint());
        WebSocketManager.startServer("localhost", 8080);
    }
}
