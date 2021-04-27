package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.ChannelLeaveEvent;
import de.kleindev.twitchbot.TwitchBot;
import de.kleindev.twitchbot.external.twitch.events.chat.TwitchChannelLeaveEvent;
import de.kleindev.twitchbot.logging.Logger;

public class ChannelLeaveEventListener {

    @EventSubscriber
    public void onEvent(ChannelLeaveEvent e){
        TwitchBot.getInstance().getEventManager().callEvent(new TwitchChannelLeaveEvent(e));
        Logger.debug("ChannelLeaveEvent");
    }
}
