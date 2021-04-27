package de.kleindev.twitchbot.client.websocket.packets.update;

import lombok.Getter;
import lombok.Setter;
import old.websocket.packets.Packet;

@Getter
@Setter
public class UpdateChangelogPackage extends Packet {
    private String version;
    private String changelog;
}
