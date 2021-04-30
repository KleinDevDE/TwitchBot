package de.kleindev.twitchbot.websocket.endpoints;

import com.j256.twofactorauth.TimeBasedOneTimePasswordUtil;
import de.kleindev.twitchbot.Main;
import de.kleindev.twitchbot.TwitchBot;
import de.kleindev.twitchbot.helpers.GsonHelper;
import de.kleindev.twitchbot.logging.LogType;
import de.kleindev.twitchbot.logging.Logger;
import de.kleindev.twitchbot.managers.UserManager;
import de.kleindev.twitchbot.objects.databases.base.objects.KeyPair;
import de.kleindev.twitchbot.objects.user.RegisteredUser;
import de.kleindev.twitchbot.websocket.Endpoint;
import de.kleindev.twitchbot.websocket.Packet;
import de.kleindev.twitchbot.websocket.WebSocketEndpoint;
import de.kleindev.twitchbot.websocket.packets.auth.*;
import org.java_websocket.WebSocket;

//FIXME
@Endpoint(address = "auth", aliases = {"login", "logout", "register"})
public class AuthEndpoint extends WebSocketEndpoint {
    @Override
    public void onMessage(WebSocket webSocket, Packet packet) {
        if (packet instanceof AuthLoginPacket){
            AuthLoginPacket authLoginPacket = (AuthLoginPacket) packet;
            RegisteredUser registeredUser = UserManager.login(authLoginPacket.getSessionID(), authLoginPacket.getUsername(), authLoginPacket.getPassword());
            if (registeredUser == null){
                InvalidLoginDataPacket invalidLoginDataPacket = new InvalidLoginDataPacket();
                webSocket.send(invalidLoginDataPacket.getSendableString());
                return;
            }
        } else if (packet instanceof AuthRegisterPacket){

        } else if (packet instanceof AuthForgotPasswordPacket){

        } else if (packet instanceof AuthRequest2FATokenPacket){

        } else if (packet instanceof AuthSubmit2FACodePacket){

        }


        if (packet instanceof AuthPacket){
            AuthPacket authPacket = (AuthPacket) packet;
            Logger.log(LogType.DEBUG, GsonHelper.toJson(authPacket));

            if (authPacket.getAuthType() == AuthType.LOGIN) {
                if (!Main.getCommandLine().hasOption("disableAuthentication")){

                }
                //TODO
            } else if (authPacket.getAuthType() == AuthType.GENERATE_TWOFA) {
                String secret = TimeBasedOneTimePasswordUtil.generateBase32Secret();
//                EncryptedText encryptedText = new EncryptedText();
                TwitchBot.getInstance().getDatabaseManager().getSQLite3Connection("data").insert("2fa", new KeyPair<>("user", ""), new KeyPair<>("secret", ""));
                webSocket.send(new TwoFADataPacket("TwitchBot (" + authPacket.getData()[1] + ")", secret).getSendableString());
                Logger.log(LogType.DEBUG, "2FA Data sent!");

            } else if (authPacket.getAuthType() == AuthType.TWOFA_TOKEN) {
                //TODO Verify token
                Logger.log(LogType.DEBUG, "2FA Data sent!");

            } else if (authPacket.getAuthType() == AuthType.REGISTER) {
//TODO
            } else if (authPacket.getAuthType() == AuthType.FORGOT_PASSWORD) {
//TODO
            } else if (authPacket.getAuthType() == AuthType.LOGOUT) {
//TODO
            } else {
                Logger.log(LogType.DEBUG, "Invalid Packet");
                sendInvalidDataPacket(webSocket);
            }
        } else {
            sendInvalidDataPacket(webSocket);
        }
    }

    private RegisteredUser getUserByPacket(Packet packet){
        //        return TwitchBot.getInstance()
    }
}
