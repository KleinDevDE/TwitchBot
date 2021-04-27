package de.kleindev.twitchbot.client.websocket.packets.twitch;

import de.kleindev.twitchbot.client.objects.twitch.ChannelPointReward;
import lombok.Getter;
import lombok.Setter;
import old.websocket.packets.Packet;

@Getter
@Setter
public class TwitchChannelPointsRedeemedPacket extends Packet {
    private String username;

    private ChannelPointReward channelPointReward;
}
