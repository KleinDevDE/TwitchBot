package de.kleindev.twitchbot.websocket.packets.twitch;

import de.kleindev.twitchbot.websocket.Packet;
import lombok.Getter;

@Getter
public class TwitchChatMessagePacket extends Packet {
    //TODO badges
    private String username;
    private String message;
}
