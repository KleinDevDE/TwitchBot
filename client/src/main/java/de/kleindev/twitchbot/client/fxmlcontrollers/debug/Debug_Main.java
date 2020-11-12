package de.kleindev.twitchbot.client.fxmlcontrollers.debug;

import de.kleindev.twitchbot.client.websocket.WebSocketManager;
import de.kleindev.twitchbot.client.websocket.packets.auth.AuthPacket;
import de.kleindev.twitchbot.client.websocket.packets.auth.AuthType;
import de.kleindev.twitchbot.client.websocket.packets.ping.PingPacket;
import javafx.scene.input.MouseEvent;

public class Debug_Main {
    public void openLoginPage(MouseEvent mouseEvent) {
        Debug_Login.show(false);
    }

    public void ping(MouseEvent mouseEvent) {
        WebSocketManager.getClient("ping").send(new PingPacket().getSendableString());
    }

    public void send2FARequest(MouseEvent mouseEvent) {
        WebSocketManager.getClient("auth").send(new AuthPacket(AuthType.GENERATE_TWOFA, new String[]{null, "KleinDev"}).getSendableString());
    }
}
