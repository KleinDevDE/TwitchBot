package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.DonationEvent;

public class DonationEventListener {

    @EventSubscriber
    public void onEvent(DonationEvent e){
//        TwitchBot.getInstance().sendPublicMessage("Danke für deine "+e.getCurrency().getDisplayName()+" " + e.getUser().getName()+"!");
    }
}
