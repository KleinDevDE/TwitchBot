package de.kleindev.twitchbot.client.websocket.packets.chat;

import de.kleindev.twitchbot.client.websocket.Packet;
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
