package de.kleindev.twitchbot.server.websocket.endpoints;

import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;
import de.kleindev.twitchbot.server.helpers.GsonHelper;
import de.kleindev.twitchbot.server.logging.LogType;
import de.kleindev.twitchbot.server.logging.Logger;
import de.kleindev.twitchbot.server.websocket.Endpoint;
import de.kleindev.twitchbot.server.websocket.Packet;
import de.kleindev.twitchbot.server.websocket.WebSocketEndpoint;
import de.kleindev.twitchbot.server.websocket.packets.auth.AuthPacket;
import de.kleindev.twitchbot.server.websocket.packets.auth.AuthType;
import de.kleindev.twitchbot.server.websocket.packets.auth.TwoFADataPacket;
import org.java_websocket.WebSocket;

@Endpoint(address = "auth", aliases = {"login", "logout", "register"})
public class AuthEndpoint extends WebSocketEndpoint {
    @Override
    public void onMessage(WebSocket webSocket, Packet packet) {
        if (packet instanceof AuthPacket){
            AuthPacket authPacket = (AuthPacket) packet;
            Logger.log(LogType.DEBUG, GsonHelper.toJson(authPacket));
            if (authPacket.getAuthType() == AuthType.GENERATE_TWOFA){
                String secret = TimeBasedOneTimePasswordUtil.generateBase32Secret();
                webSocket.send(new TwoFADataPacket("TwitchBot ("+authPacket.getData()[1]+")", secret).getSendableString());
                Logger.log(LogType.DEBUG, "2FA Data sent!");
            } else if (authPacket.getAuthType() == AuthType.TWOFA_TOKEN){
                String secret = TimeBasedOneTimePasswordUtil.generateBase32Secret();
                webSocket.send(new TwoFADataPacket("TwitchBot ("+authPacket.getData()[1]+")", secret).getSendableString());
                Logger.log(LogType.DEBUG, "2FA Data sent!");
            } else {
                Logger.log(LogType.DEBUG, "Invalid Packet");
                sendInvalidDataPacket(webSocket);
            }
        } else {
            sendInvalidDataPacket(webSocket);
        }
    }
}
