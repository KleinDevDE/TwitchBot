package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.pubsub.events.RewardRedeemedEvent;
import de.kleindev.twitchbot.logging.Logger;

public class RewardRedeemedEventListener {

    @EventSubscriber
    public void onEvent(RewardRedeemedEvent e){
        Logger.debug("RewardRedeemedEvent");
    }
}
