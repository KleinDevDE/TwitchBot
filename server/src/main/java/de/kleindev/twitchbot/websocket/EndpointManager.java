package de.kleindev.twitchbot.websocket;


import de.kleindev.twitchbot.logging.LogType;
import de.kleindev.twitchbot.logging.Logger;
import de.kleindev.twitchbot.websocket.exceptions.InvalidWebSocketEndpointClassException;

import java.util.HashMap;

public class EndpointManager {
    private static HashMap<String, WebSocketEndpoint> webSocketEndpointHashMap = new HashMap<>();
    private static HashMap<String, String> endpointAliasHashMap = new HashMap<>();

    public static void registerEndpoint(WebSocketEndpoint webSocketEndpoint) throws InvalidWebSocketEndpointClassException {
        if (!webSocketEndpoint.getClass().isAnnotationPresent(Endpoint.class))
            throw new InvalidWebSocketEndpointClassException("Annotation \"@Endpoint\" is not present on class \""+webSocketEndpoint.getClass().getSimpleName()+"\"!");

        Endpoint endpoint = webSocketEndpoint.getClass().getAnnotation(Endpoint.class);
        webSocketEndpointHashMap.put(endpoint.address(), webSocketEndpoint);
        for (String s : endpoint.aliases()){
            if (endpointAliasHashMap.containsKey(s)){
                Logger.log(LogType.WARNING, "Alias \""+s+"\" is already registered for the endpoint \""+endpointAliasHashMap.get(s)+"\"!");
                continue;
            }
            if (webSocketEndpointHashMap.containsKey(s)){
                Logger.log(LogType.WARNING, "Alias \""+s+"\" is a previously set endpoint!");
                continue;
            }
            endpointAliasHashMap.put(s, endpoint.address());
        }
    }

    public static boolean isValidEndpoint(String resourceDescriptor){
        return webSocketEndpointHashMap.containsKey(resourceDescriptor.toLowerCase());
    }

    public static WebSocketEndpoint getEndpoint(String resourceDescriptor){
        return webSocketEndpointHashMap.get(resourceDescriptor.toLowerCase());
    }

    public void a(String required, String optional){

    }

    public void a(String required){
        a(required, "Hallo");
    }
}
