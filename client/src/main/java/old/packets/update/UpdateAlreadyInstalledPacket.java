package old.packets.update;

import old.packets.Packet;

public class UpdateAlreadyInstalledPacket extends Packet {
    private String clientVersion;
    private String updateVersion;
    private String changelog;
    private long releaseDate;

    public UpdateAlreadyInstalledPacket(){

    }

    public UpdateAlreadyInstalledPacket(String clientVersion, String updateVersion, String changelog, long releaseDate){
        this.clientVersion = clientVersion;
        this.updateVersion = updateVersion;
        this.changelog = changelog;
        this.releaseDate = releaseDate;
    }

    public UpdateAlreadyInstalledPacket putClientVersion(String clientVersion){
        this.clientVersion = clientVersion;
        return this;
    }

    public UpdateAlreadyInstalledPacket putUpdateVersion(String updateVersion){
        this.updateVersion = updateVersion;
        return this;
    }

    public UpdateAlreadyInstalledPacket putChangelog(String changelog){
        this.changelog = changelog;
        return this;
    }

    public UpdateAlreadyInstalledPacket putReleaseDate(long releaseDate){
        this.releaseDate = releaseDate;
        return this;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public String getUpdateVersion() {
        return updateVersion;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public String getChangelog() {
        return changelog;
    }
}
