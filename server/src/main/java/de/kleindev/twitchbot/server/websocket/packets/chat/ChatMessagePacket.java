package de.kleindev.twitchbot.server.websocket.packets.chat;

import de.kleindev.twitchbot.server.websocket.Packet;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class ChatMessagePacket extends Packet {
    @NonNull
    private String message;

    public ChatMessagePacket(String message){
        this.message = message;
    }
}
