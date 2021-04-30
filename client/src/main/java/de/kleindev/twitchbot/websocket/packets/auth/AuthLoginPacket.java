package de.kleindev.twitchbot.websocket.packets.auth;

import de.kleindev.twitchbot.websocket.Packet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthLoginPacket extends Packet {
    private String username;
    private String password;
}
