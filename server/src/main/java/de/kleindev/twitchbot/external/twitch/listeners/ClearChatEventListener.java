package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.ClearChatEvent;

public class ClearChatEventListener {

    @EventSubscriber
    public void onEvent(ClearChatEvent e){

    }
}
