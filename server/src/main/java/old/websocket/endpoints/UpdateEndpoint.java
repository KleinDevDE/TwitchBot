package old.websocket.endpoints;

import old.websocket.packets.Packet;
import old.websocket.packets.update.CheckVersionPacket;
import old.websocket.packets.update.DownloadUpdatePacket;
import old.websocket.packets.general.WrongEndpointPacket;
import old.websocket.packets.update.UpdateAlreadyInstalledPacket;
import old.websocket.packets.update.UpdateAvailablePacket;
import old.websocket.packets.update.UpdateFilePacket;
import org.java_websocket.WebSocket;

import java.io.File;

@ServerEndpoint(endpoint = "update")
public class UpdateEndpoint extends WebSocketEndpoint{

    @Override
    public void onMessage(Packet packet, WebSocket webSocket) {
        System.out.println("UpdateEndpoint called with packet: " + packet.getClass().getSimpleName());
        if (packet instanceof CheckVersionPacket){
            CheckVersionPacket checkVersionPacket = (CheckVersionPacket) packet;
            //TODO Check for new version

            if(true){
                UpdateAvailablePacket updateAvailablePacket = new UpdateAvailablePacket("1.0", "In your pussy!", System.currentTimeMillis());
                updateAvailablePacket.send(webSocket);
            } else {
                UpdateAlreadyInstalledPacket updateAlreadyInstalledPacket = new UpdateAlreadyInstalledPacket();
                updateAlreadyInstalledPacket.send(webSocket);
            }
        } else if (packet instanceof DownloadUpdatePacket){
            UpdateFilePacket updateFilePacket = new UpdateFilePacket(new File("")); //TODO set latest clientVersion-File
            updateFilePacket.send(webSocket);
        } else {
            new WrongEndpointPacket(this).send(webSocket);
        }
    }
}
