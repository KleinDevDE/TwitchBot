package old.websocket.packets.login.error;

import old.websocket.packets.Packet;

import java.util.UUID;

public class WrongPasswordPacket extends Packet {

    @Override
    public void setSessionID(UUID sessionID) {
        super.setSessionID(sessionID);
    }
}
