package kryonet;

import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.start();
        // TCP 54555, UDP 54777
        client.connect(5000, "127.0.0.1", 54555, 54777);
        client.sendTCP("Hallo von TCP!");
        client.sendUDP("Hallo von UDP!");
        client.run();
    }
}
