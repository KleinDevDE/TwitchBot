package old.websocket.packets.commands;

import old.websocket.packets.Packet;

import java.util.UUID;

public class CommandRegistrationConfirmedPacket extends Packet {
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public void setSessionID(UUID sessionID) {
        super.setSessionID(sessionID);
    }

}
