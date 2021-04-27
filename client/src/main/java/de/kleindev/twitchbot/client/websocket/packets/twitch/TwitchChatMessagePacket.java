package de.kleindev.twitchbot.client.websocket.packets.twitch;

import lombok.Getter;
import old.websocket.packets.Packet;

@Getter
public class TwitchChatMessagePacket extends Packet {
    //TODO badges
    private String username;
    private String message;
}
