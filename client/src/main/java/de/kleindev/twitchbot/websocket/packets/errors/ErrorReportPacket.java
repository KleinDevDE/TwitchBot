package de.kleindev.twitchbot.websocket.packets.errors;

import de.kleindev.twitchbot.objects.application.OSInformation;
import de.kleindev.twitchbot.websocket.Packet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorReportPacket extends Packet {
    private Exception exception;
    private OSInformation osInformation;
}
