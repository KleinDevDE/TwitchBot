package old.utils.managers;

import old.utils.Session;
import org.java_websocket.WebSocket;


import java.util.*;

public class SessionManager {
    private static HashMap<UUID, Session> sessionHashMap = new HashMap<>();

    public static Session getSession(UUID sessionID){
        return sessionHashMap.get(sessionID);
    }

    public static void registerSession(UUID sessionID, String username, WebSocket webSocket){
        sessionHashMap.put(sessionID, new Session(webSocket, sessionID, username));
    }

    public static void unregisterSession(UUID sessionID){
        sessionHashMap.remove(sessionID);
    }

    public static Collection<Session> getSessions(){
        return sessionHashMap.values();
    }

    public static Set<Session> getSessionsByUsername(String username){
        Set<Session> sessions = new HashSet<>();
        for(Session s : sessionHashMap.values()){
            if(s.getUsername().equalsIgnoreCase(username)){
                sessions.add(s);
            }
        }
        return sessions;
    }

    public static boolean isValidSession(UUID uuid){
        return sessionHashMap.containsKey(uuid);
    }
}
