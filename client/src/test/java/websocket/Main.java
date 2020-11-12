package websocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        Client client = new Client(new URI("ws://localhost:8080/update"));
        UUID clientID = UUID.randomUUID();
        System.out.println(client.toString());
        client.addHeader("clientID", client.toString());
        client.connect();
        while (!client.isOpen()){}
        client.sendPing();
    }
}
