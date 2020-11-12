package kryonet;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
        // TCP 54555, UDP 54777
        server.bind(54555, 54777);
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof String)
                    System.out.println((String)object);
            }
        });
    }
}
