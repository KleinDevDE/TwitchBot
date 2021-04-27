package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import de.kleindev.twitchbot.TwitchBot;
import de.kleindev.twitchbot.external.twitch.events.chat.TwitchChannelMessageEvent;

import java.util.List;

public class ChannelMessageEventListener {

    @EventSubscriber
    public void onEvent(ChannelMessageEvent e){
        TwitchBot.getInstance().getEventManager().callEvent(new TwitchChannelMessageEvent(e));

        // TODO Command handling
        // TODO Permission handling
    }
}
