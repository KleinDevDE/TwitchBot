package old.websocket.packets.commands;

import old.objects.command.Command;
import old.websocket.packets.Packet;

import java.util.List;
import java.util.UUID;

public class ListCommandsPacket extends Packet {
    private List<Command> commands;

    public List<Command> getCommands() {
        return commands;
    }

    @Override
    public void setSessionID(UUID sessionID) {
        super.setSessionID(sessionID);
    }
}
