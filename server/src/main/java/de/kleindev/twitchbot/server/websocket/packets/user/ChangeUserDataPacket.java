package de.kleindev.twitchbot.server.websocket.packets.user;

import de.kleindev.twitchbot.server.websocket.Packet;

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
