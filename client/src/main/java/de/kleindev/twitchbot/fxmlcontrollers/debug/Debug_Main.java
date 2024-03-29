package de.kleindev.twitchbot.fxmlcontrollers.debug;

import de.kleindev.twitchbot.websocket.packets.auth.AuthPacket;
import de.kleindev.twitchbot.websocket.packets.auth.AuthType;
import de.kleindev.twitchbot.websocket.packets.ping.PingPacket;
import javafx.scene.input.MouseEvent;

public class Debug_Main {
    public void openLoginPage(MouseEvent mouseEvent) {
        Debug_Login.show(false);
    }

    public void ping(MouseEvent mouseEvent) {
        new PingPacket().send("ping");
    }

    public void send2FARequest(MouseEvent mouseEvent) {
        new AuthPacket(AuthType.GENERATE_TWOFA, new String[]{null, "KleinDev"}).send("auth");
    }
}
