package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.UserTimeoutEvent;

public class UserTimeoutEventListener {

    @EventSubscriber
    public void onEvent(UserTimeoutEvent e){
    }
}
