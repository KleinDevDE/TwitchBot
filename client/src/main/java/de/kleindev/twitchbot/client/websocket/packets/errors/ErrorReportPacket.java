package de.kleindev.twitchbot.client.websocket.packets.errors;

import de.kleindev.twitchbot.client.objects.application.OSInformation;
import de.kleindev.twitchbot.client.websocket.Packet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorReportPacket extends Packet {
    private Exception exception;
    private OSInformation osInformation;
}
