package de.kleindev.twitchbot.websocket.packets.update;

import de.kleindev.twitchbot.objects.application.UpdateType;
import de.kleindev.twitchbot.websocket.Packet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVersionStatusPackage extends Packet {
    private String[] oldClientVersion;
    private String[] newClientVersion;
    private boolean needsUpdate;
    private UpdateType updateType;

    public boolean isMajorUpdate(){
        return Integer.parseInt(oldClientVersion[0]) < Integer.parseInt(newClientVersion[0]);
    }

    public boolean isMinorUpdate(){
        return Integer.parseInt(oldClientVersion[1]) < Integer.parseInt(newClientVersion[1]);
    }

    public boolean isPatchUpdate(){
        return Integer.parseInt(oldClientVersion[2]) < Integer.parseInt(newClientVersion[2]);
    }

    public boolean needsUpdate(){
        return isMajorUpdate() || isMinorUpdate() || isPatchUpdate();
    }
}
