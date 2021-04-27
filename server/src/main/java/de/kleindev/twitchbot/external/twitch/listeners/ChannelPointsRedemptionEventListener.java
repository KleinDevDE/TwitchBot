package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.pubsub.events.ChannelPointsRedemptionEvent;
import de.kleindev.twitchbot.logging.Logger;

public class ChannelPointsRedemptionEventListener {
    public static boolean enabled = true;

    @EventSubscriber
    public void onEvent(ChannelPointsRedemptionEvent e){
        if(!enabled)
            return;
        Logger.debug("New ChannelPointRedemption!\n" +
                "Title: " + e.getRedemption().getReward().getTitle()+"\n" +
                "ID: " + e.getRedemption().getReward().getId() + "\n" +
                "User: " + e.getRedemption().getUser().getDisplayName()+"\n" +
                "Cost: " + e.getRedemption().getReward().getCost()+"\n" +
                "Message: " + e.getRedemption().getUserInput()+"\n" +
                "Status: " + e.getRedemption().getStatus());
        if (e.getRedemption().getStatus().equals("ACTION_TAKEN"))
            return;

//        if (TwitchBot.getInstance().getData().getChannelPointRewardManager().rewardExists(e.getRedemption().getReward().getTitle()))
//            TwitchBot.getInstance().getData().getChannelPointRewardManager().getReward(e.getRedemption().getReward().getTitle()).run(e.getRedemption());
    }
}
