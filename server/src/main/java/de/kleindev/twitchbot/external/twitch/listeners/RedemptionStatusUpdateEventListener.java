package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.pubsub.events.RedemptionStatusUpdateEvent;
import de.kleindev.twitchbot.logging.Logger;


public class RedemptionStatusUpdateEventListener {

    @EventSubscriber
    public void onEvent(RedemptionStatusUpdateEvent e){
        Logger.debug("RedemptionStatusUpdateEvent");
    }
}
