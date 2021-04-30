package de.kleindev.twitchbot.websocket.endpoints;

import de.kleindev.twitchbot.utils.DevTweaks;
import de.kleindev.twitchbot.websocket.Endpoint;
import de.kleindev.twitchbot.websocket.Packet;
import de.kleindev.twitchbot.websocket.WebSocketEndpoint;
import de.kleindev.twitchbot.websocket.packets.update.UpdateChangelogPackage;
import de.kleindev.twitchbot.websocket.packets.update.UpdateFilePackage;
import de.kleindev.twitchbot.websocket.packets.update.UpdateVersionStatusPackage;
import org.java_websocket.WebSocket;

import java.io.File;

@Endpoint(address = "update")
public class UpdateEndpoint extends WebSocketEndpoint {

    @Override
    public void onMessage(WebSocket webSocket, Packet packet) {
        if (packet instanceof UpdateVersionStatusPackage){
            UpdateVersionStatusPackage updateVersionStatusPackage = (UpdateVersionStatusPackage) packet;
            updateVersionStatusPackage.setNewClientVersion(new String[]{"1", "0", "1"});
            webSocket.send(updateVersionStatusPackage.getSendableString());
        } else if (packet instanceof UpdateChangelogPackage){
            UpdateChangelogPackage updateChangelogPackage = (UpdateChangelogPackage) packet;
            updateChangelogPackage.setChangelog("BlaBla Changelog\nBlaBla");
            webSocket.send(updateChangelogPackage.getSendableString());
        } else if (packet instanceof UpdateFilePackage){
            UpdateFilePackage updateFilePackage = (UpdateFilePackage) packet;
            updateFilePackage.setVersion(new String[]{"1", "0", "1"});
            updateFilePackage.setFileInBase64(DevTweaks.encodeFileToBase64Binary(new File("update.jar")));
            webSocket.send(updateFilePackage.getSendableString());
        }
        // TODO Check for updates, get changelog, get latest update
    }
}
