package de.kleindev.twitchbot.objects.twitch;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelPointReward {
    private String rewardName;
    private String rewardDescription;
    private int rewardCostPoints;
}
