package old.websocket.packets.login;

import old.websocket.packets.Packet;

import java.util.UUID;

public class LoginConfirmedPacket extends Packet {
    private String username;

    public String getUsername() {
        //TODO decrypt
        return username;
    }

    public void setUsername(String username) {
        //TODO encrypt
        this.username = username;
    }

    @Override
    public void setSessionID(UUID sessionID) {
        super.setSessionID(sessionID);
    }
}
