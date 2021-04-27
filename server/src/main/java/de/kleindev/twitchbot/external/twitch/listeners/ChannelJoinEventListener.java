package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.ChannelJoinEvent;
import de.kleindev.twitchbot.TwitchBot;
import de.kleindev.twitchbot.external.twitch.events.chat.TwitchChannelJoinEvent;
import de.kleindev.twitchbot.logging.Logger;

public class ChannelJoinEventListener {

    @EventSubscriber
    public void onEvent(ChannelJoinEvent e){
        TwitchBot.getInstance().getEventManager().callEvent(new TwitchChannelJoinEvent(e));
        Logger.debug("ChannelJoinEvent");
    }
}
