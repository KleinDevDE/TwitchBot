package de.kleindev.twitchbot.websocket.packets.update;

import de.kleindev.twitchbot.websocket.Packet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateChangelogPackage extends Packet {
    private String version;
    private String changelog;
}
