package de.kleindev.twitchbot.client.websocket.packets.user;

import de.kleindev.twitchbot.client.websocket.Packet;

public class ChangeUserDataPacket extends Packet {
    private UserDataType dataType;
    private String data;

    public UserDataType getDataType() {
        return dataType;
    }

    public String getData() {
        return data;
    }
}
