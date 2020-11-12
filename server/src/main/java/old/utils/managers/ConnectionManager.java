package old.utils.managers;

import old.utils.connections.Connection;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {
    private static HashMap<String, Connection> connectionHashMap = new HashMap<>();

    public static void registerConnection(String id, Connection connection){
        connectionHashMap.put(id, connection);
    }

    public static Connection getConnection(String id){
        return connectionHashMap.get(id);
    }

    public static void closeConnections(){
        for(Map.Entry<String, Connection> c : connectionHashMap.entrySet()){
            // "Closing connection \""+c.getKey()+"\""
            c.getValue().disconnect();
        }
    }

    public static void openConnections(){
        for(Map.Entry<String, Connection> c : connectionHashMap.entrySet()){
            // "Opening connection \""+c.getKey()+"\""
            c.getValue().connect();
        }
    }
}
