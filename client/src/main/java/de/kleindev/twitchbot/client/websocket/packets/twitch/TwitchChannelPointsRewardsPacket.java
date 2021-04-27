package de.kleindev.twitchbot.client.websocket.packets.twitch;

import de.kleindev.twitchbot.client.objects.twitch.ChannelPointReward;
import lombok.Getter;
import lombok.Setter;
import old.websocket.packets.Packet;

import java.util.List;

@Getter
@Setter
public class TwitchChannelPointsRewardsPacket extends Packet {
    private List<ChannelPointReward> channelPointRewards;
}
