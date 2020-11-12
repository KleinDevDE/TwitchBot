package old.utils.managers;

import old.websocket.WSServer;
import org.java_websocket.WebSocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;

public class WebSocketManager {
    private static WSServer wsServer;

    public static void startServer(String host, int port){
        wsServer = new WSServer(new InetSocketAddress(host, port));
        wsServer.setConnectionLostTimeout(60);
        wsServer.setMaxPendingConnections(500);
        wsServer.start();
    }

    public static void stopServer() throws IOException, InterruptedException {
        wsServer.stop();
    }

    public static void broadcast(String message){
        wsServer.broadcast(message);
    }

    public Collection<WebSocket> getConnections(){
        return wsServer.getConnections();
    }
}
