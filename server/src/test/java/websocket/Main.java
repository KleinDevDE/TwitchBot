package websocket;

import de.kleindev.twitchbot.server.websocket.packets.auth.AuthPacket;
import de.kleindev.twitchbot.server.websocket.packets.ping.PingPacket;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        PingSocket pingSocket = new PingSocket(new URI("ws://localhost:8081/ping"));
        pingSocket.connect();
        pingSocket.send(new PingPacket().getSendableString());
        pingSocket.send(new AuthPacket().getSendableString());
    }
}
