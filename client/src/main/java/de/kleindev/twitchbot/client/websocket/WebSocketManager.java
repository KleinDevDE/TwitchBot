package de.kleindev.twitchbot.client.websocket;

import org.java_websocket.client.WebSocketClient;

import java.util.HashMap;

public class WebSocketManager {
    private static HashMap<String, WebSocketClient> webSocketClientHashMap = new HashMap<>();

    public static void registerClient(WebSocketClient webSocketClient){
        String endpoint = webSocketClient.getResourceDescriptor().replaceFirst("/", "");
        if (webSocketClientHashMap.containsKey(endpoint))
            webSocketClientHashMap.get(endpoint).closeConnection(0, "WebSocketManager.registerClient() called, endpoint will be reconnected");
        webSocketClientHashMap.put(endpoint, webSocketClient);
        System.out.println("New WebSocketClient registered! ("+webSocketClient.getURI()+")");
    }

    public static WebSocketClient getClient(String endpoint){
        return webSocketClientHashMap.get(endpoint);
    }
}
