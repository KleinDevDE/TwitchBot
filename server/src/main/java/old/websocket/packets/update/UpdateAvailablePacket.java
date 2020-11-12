package old.websocket.packets.update;

import old.websocket.packets.Packet;

public class UpdateAvailablePacket extends Packet {
    private String version;
    private String changelog; //TODO in HTML
    private long releaseDate;

    public UpdateAvailablePacket(){

    }

    public UpdateAvailablePacket(String version, String changelog, long releaseDate){
        this.version = version;
        this.changelog = changelog;
        this.releaseDate = releaseDate;
    }

    public UpdateAvailablePacket putVersion(String version){
        this.version = version;
        return this;
    }

    public UpdateAvailablePacket putChangelog(String changelog){
        this.changelog = changelog;
        return this;
    }

    public UpdateAvailablePacket putReleaseDate(long releaseDate){
        this.releaseDate = releaseDate;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public String getChangelog() {
        return changelog;
    }
}
