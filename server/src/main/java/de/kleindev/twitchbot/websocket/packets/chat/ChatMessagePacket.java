package de.kleindev.twitchbot.websocket.packets.chat;

import de.kleindev.twitchbot.websocket.Packet;
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
