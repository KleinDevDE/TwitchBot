package old.websocket.packets.commands;

import old.objects.command.Command;
import old.websocket.packets.Packet;

public class NewCommandPacket extends Packet {
    private Command command;

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
