package old.websocket.packets.login.error;

import old.websocket.packets.Packet;

public class UsernameAlreadyRegisteredPacket extends Packet {
    private final String username;

    public UsernameAlreadyRegisteredPacket(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
