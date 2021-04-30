package de.kleindev.twitchbot.websocket.packets.auth;

import de.kleindev.twitchbot.websocket.Packet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthTooManyAttemptsPacket extends Packet {
    private long blockedUntil;
}
