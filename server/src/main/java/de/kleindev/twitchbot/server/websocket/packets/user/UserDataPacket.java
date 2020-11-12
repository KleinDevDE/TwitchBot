package de.kleindev.twitchbot.server.websocket.packets.user;

import de.kleindev.twitchbot.server.websocket.Packet;

import java.util.HashMap;

public class UserDataPacket extends Packet {
    private HashMap<UserDataType, Integer> userDataTypes = new HashMap<>();
    private String[] data = new String[UserDataType.values().length];

    public String getData(UserDataType userDataType){
        return data[userDataTypes.get(userDataType)];
    }

    public void addData(UserDataType userDataType, String data){
        if(userDataTypes.containsKey(userDataType))
            this.data[userDataTypes.get(userDataType)] = data;
        userDataTypes.put(userDataType, this.data.length+1);
        this.data[this.data.length+1] = data;
    }
}
