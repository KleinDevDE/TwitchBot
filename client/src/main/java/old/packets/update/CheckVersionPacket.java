package old.packets.update;

import old.packets.Packet;

public class CheckVersionPacket extends Packet {
    private String version;
    private String checksum = "";

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
