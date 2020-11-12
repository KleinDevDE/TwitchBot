package old.websocket.packets.update;

import old.utils.managers.ApplicationManager;
import old.websocket.packets.Packet;

public class CheckVersionPacket extends Packet {
    private String version;
    private String checksum = ApplicationManager.getCheckSum();

    public CheckVersionPacket(String version){
        this.version = version;
    }

    public CheckVersionPacket putVersion(String version){
        this.version = version;
        return this;
    }

    public String getVersion() {
        return version;
    }
}
