package old;

import old.packets.login.LoginPacket;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        Client client = new Client(new URI("ws://localhost:8080/login"));
        client.connect();
        LoginPacket loginPacket = new LoginPacket();
        while (!client.isOpen()){}
        loginPacket.send(client);
    }
}
