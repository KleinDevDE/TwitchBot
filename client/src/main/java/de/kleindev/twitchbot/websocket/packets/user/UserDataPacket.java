package de.kleindev.twitchbot.websocket.packets.user;

import de.kleindev.twitchbot.websocket.Packet;

import java.util.HashMap;

public class UserDataPacket extends Packet {
    private HashMap<UserDataType, String> userData = new HashMap<>();

    public String getData(UserDataType userDataType){
        return userData.get(userDataType);
    }

    public void addData(UserDataType userDataType, String data){
        userData.put(userDataType, data);
    }
}
