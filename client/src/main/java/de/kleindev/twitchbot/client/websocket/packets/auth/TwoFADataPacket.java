package de.kleindev.twitchbot.client.websocket.packets.auth;

import de.kleindev.twitchbot.client.websocket.Packet;

public class TwoFADataPacket extends Packet {
    private String name;
    private String secret;

    public TwoFADataPacket(String name, String secret){
        this.name = name;
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public String getSecret() {
        return secret;
    }
}
