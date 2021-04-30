package de.kleindev.twitchbot.websocket.packets.twitch;

import de.kleindev.twitchbot.objects.twitch.ChannelPointReward;
import de.kleindev.twitchbot.websocket.Packet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwitchChannelPointsRedeemedPacket extends Packet {
    private String username;

    private ChannelPointReward channelPointReward;
}
