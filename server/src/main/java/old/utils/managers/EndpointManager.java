package old.utils.managers;

import old.websocket.endpoints.ServerEndpoint;
import old.websocket.endpoints.WebSocketEndpoint;
import old.utils.exceptions.InvalidWebSocketEndpointClassException;

import java.util.HashMap;

public class EndpointManager {
    private static HashMap<String, WebSocketEndpoint> webSocketEndpointHashMap = new HashMap<>();

    public static void registerEndpoint(WebSocketEndpoint webSocketEndpoint) throws InvalidWebSocketEndpointClassException {
        if (!webSocketEndpoint.getClass().isAnnotationPresent(ServerEndpoint.class))
            throw new InvalidWebSocketEndpointClassException("Annotation \"@ServerEndpoint\" is not present on class \""+webSocketEndpoint.getClass().getSimpleName()+"\"!");

        ServerEndpoint serverEndpoint = webSocketEndpoint.getClass().getAnnotation(ServerEndpoint.class);
        webSocketEndpointHashMap.put(serverEndpoint.endpoint(), webSocketEndpoint);
    }

    public static boolean isValidEndpoint(String resourceDescriptor){
        return webSocketEndpointHashMap.containsKey(resourceDescriptor.toLowerCase());
    }

    public static WebSocketEndpoint getEndpoint(String resourceDescriptor){
        return webSocketEndpointHashMap.get(resourceDescriptor.toLowerCase());
    }
}
