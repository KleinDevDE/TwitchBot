package old.utils;

import org.java_websocket.WebSocket;

import java.util.UUID;

public class Session {
    private UUID sessionID;
    private long loginTime;
    private String username;
    private WebSocket webSocket;

    public Session(WebSocket webSocket, UUID sessionID, String username){
        this.webSocket = webSocket;
        this.sessionID = sessionID;
        this.username = username;
        loginTime = System.currentTimeMillis();
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public String getUsername() {
        return username;
    }

    public UUID getSessionID() {
        return sessionID;
    }
}
