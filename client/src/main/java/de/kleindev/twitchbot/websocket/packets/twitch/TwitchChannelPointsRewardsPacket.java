package de.kleindev.twitchbot.websocket.packets.twitch;

import de.kleindev.twitchbot.objects.twitch.ChannelPointReward;
import de.kleindev.twitchbot.websocket.Packet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TwitchChannelPointsRewardsPacket extends Packet {
    private List<ChannelPointReward> channelPointRewards;
}
