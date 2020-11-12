package de.kleindev.twitchbot.server.websocket;

import com.google.gson.Gson;
import de.kleindev.twitchbot.server.helpers.GsonHelper;
import de.kleindev.twitchbot.server.logging.LogType;
import de.kleindev.twitchbot.server.logging.Logger;
import de.kleindev.twitchbot.server.websocket.packets.errors.InvalidDataPacket;
import de.kleindev.twitchbot.server.websocket.packets.errors.InvalidEndpointPacket;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class WSServer extends WebSocketServer {
    public WSServer( InetSocketAddress address ){
        super(address);
    }


    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        //TODO handle handshake
        //TODO May add easteregg, handshake only with gloves
        Logger.log(LogType.DEBUG,
                "[WebSocket|"+conn.getResourceDescriptor().replaceFirst("/", "")+"] ("+conn.getRemoteSocketAddress().getAddress().getHostAddress()+") New connection"
        );
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        if (remote)
            Logger.log(LogType.DEBUG,
                    "[WebSocket|"+conn.getResourceDescriptor().replaceFirst("/", "")+"] ("+conn.getRemoteSocketAddress().getAddress().getHostAddress()+") Connection closed by remote.\n" +
                    reason + " ("+code+")");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Logger.log(LogType.DEBUG,
                "[WebSocket|"+conn.getResourceDescriptor().replaceFirst("/", "")+"] ("+conn.getRemoteSocketAddress().getAddress().getHostAddress()+") New message\n" +
                        "Message: " + message
        );
        if (!Packet.isValidPacket(message)){
            Logger.log(LogType.WARNING, "[WebSocket|"+conn.getResourceDescriptor().replaceFirst("/", "")+"] Invalid Data received from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
            conn.send(new InvalidDataPacket().getSendableString());
            return;
        }
        if (!EndpointManager.isValidEndpoint(conn.getResourceDescriptor().replaceFirst("/", ""))){
            Logger.log(LogType.WARNING, "[WebSocket|"+conn.getResourceDescriptor().replaceFirst("/", "")+"] ("+conn.getRemoteSocketAddress().getAddress().getHostAddress()+" invalid endpoint!");
            conn.send(new InvalidEndpointPacket().getSendableString());
            return;
        }
        Packet packet = GsonHelper.fromJson(message, Packet.class);
        packet.decrypt();
        EndpointManager.getEndpoint(conn.getResourceDescriptor().replaceFirst("/", "")).onMessage(conn, packet);
        Logger.log(LogType.WARNING, "[WebSocket|"+conn.getResourceDescriptor().replaceFirst("/", "")+"] Endpoint->onMessage() called");
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        Logger.log(LogType.DEBUG,
                "[WebSocket|"+conn.getResourceDescriptor().replaceFirst("/", "")+"] ("+conn.getRemoteSocketAddress().getAddress().getHostAddress()+") ERROR\n" +
                        "Exception: " + ex.getClass().getSimpleName() + " -> " + ex.getMessage()
        );
    }

    @Override
    public void onStart() {

    }
}
