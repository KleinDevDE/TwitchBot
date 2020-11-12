package old.websocket.packets.login.error;

import old.websocket.packets.Packet;

public class EmailAlreadyRegisteredPacket extends Packet {
    private String email;

    public EmailAlreadyRegisteredPacket(String email){
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
