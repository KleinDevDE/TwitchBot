package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.ChannelStateEvent;

public class ChannelStateEventListener {

    @EventSubscriber
    public void onEvent(ChannelStateEvent e){

    }
}
