package old.websocket.packets.login;

import old.websocket.packets.Packet;

public class RecoverAccountPacket extends Packet {
    private String email;
    private String token;

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
