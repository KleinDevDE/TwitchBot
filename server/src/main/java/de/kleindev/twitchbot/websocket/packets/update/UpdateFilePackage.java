package de.kleindev.twitchbot.websocket.packets.update;

import de.kleindev.twitchbot.websocket.Packet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFilePackage extends Packet {
    private String version;
    private String fileInBase64;
}
