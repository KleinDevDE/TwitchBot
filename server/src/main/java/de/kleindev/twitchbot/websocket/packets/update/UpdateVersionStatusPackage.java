package de.kleindev.twitchbot.websocket.packets.update;

import de.kleindev.twitchbot.objects.application.UpdateType;
import de.kleindev.twitchbot.websocket.Packet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVersionStatusPackage extends Packet {
    private String oldClientVersion;
    private String newClientVersion;
    private boolean needsUpdate;
    private UpdateType updateType;
}
