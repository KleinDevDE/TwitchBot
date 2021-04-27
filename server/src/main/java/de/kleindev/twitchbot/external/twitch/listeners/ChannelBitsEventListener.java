package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.pubsub.events.ChannelBitsEvent;
import com.google.gson.Gson;
import de.kleindev.twitchbot.TwitchBot;
import de.kleindev.twitchbot.external.twitch.events.pubsub.TwitchChannelBitsEvent;
import de.kleindev.twitchbot.logging.Logger;

public class ChannelBitsEventListener {

    @EventSubscriber
    public void onEvent(ChannelBitsEvent e){
        TwitchBot.getInstance().getEventManager().callEvent(new TwitchChannelBitsEvent(e));

        Logger.log("User \""+e.getData().getUserName()+"\" hat "+e.getData().getBitsUsed()+" Bits ausgegeben.");
        Logger.debug("ChannelBitsEvent - Data\n" +
                new Gson().toJson(e.getData()));
    }
}
