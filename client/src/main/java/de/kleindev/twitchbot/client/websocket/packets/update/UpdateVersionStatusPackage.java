package de.kleindev.twitchbot.client.websocket.packets.update;

import de.kleindev.twitchbot.client.objects.application.UpdateType;
import lombok.Getter;
import lombok.Setter;
import old.websocket.packets.Packet;

@Getter
@Setter
public class UpdateVersionStatusPackage extends Packet {
    private String oldClientVersion;
    private String newClientVersion;
    private boolean needsUpdate;
    private UpdateType updateType;
}
