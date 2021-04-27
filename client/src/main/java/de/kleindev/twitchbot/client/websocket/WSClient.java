package de.kleindev.twitchbot.client.websocket;

import de.kleindev.twitchbot.client.fxmlcontrollers.debug.Debug_2FA;
import de.kleindev.twitchbot.client.helpers.GsonHelper;
import de.kleindev.twitchbot.client.websocket.packets.auth.AuthPacket;
import de.kleindev.twitchbot.client.websocket.packets.auth.AuthType;
import de.kleindev.twitchbot.client.websocket.packets.auth.InvalidTwoFATokenPacket;
import de.kleindev.twitchbot.client.websocket.packets.auth.TwoFADataPacket;
import de.kleindev.twitchbot.client.websocket.packets.ping.PongPacket;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WSClient extends WebSocketClient {
    private String endpoint;

    public WSClient(String host, int port, String endpoint) throws URISyntaxException {
        super(new URI("ws://"+host+":"+port+"/"+endpoint));
        connect();
        this.endpoint = endpoint;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {
        System.out.print("Answer! " + message);
        if (!Packet.isValidPacket(message)){
            System.err.println("[WebSocket|"+endpoint+"] Invalid Data received from server!");
            return;
        }

        Packet packet = GsonHelper.fromJson(message, Packet.class);
        packet.decrypt();

        if (packet instanceof TwoFADataPacket){
            TwoFADataPacket twoFADataPacket = (TwoFADataPacket) packet;

            System.out.println("Open Debug_2FA");
            Platform.runLater(()-> Debug_2FA.show(false, (TwoFADataPacket) packet));
        } else if (packet instanceof AuthPacket){
            if (((AuthPacket)packet).getAuthType() == AuthType.SUCCESSFUL)
                Platform.runLater(()->new Alert(Alert.AlertType.CONFIRMATION, "2FA Token valid!").show());
        } else if (packet instanceof InvalidTwoFATokenPacket){
            Platform.runLater(()->new Alert(Alert.AlertType.WARNING, "Invalid 2FA Token!").show());
        } else if (packet instanceof PongPacket){
            Platform.runLater(()->new Alert(Alert.AlertType.CONFIRMATION, "Pong!").show());
        } else System.err.println("Invalid packet!");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
