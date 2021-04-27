package de.kleindev.twitchbot.external.twitch.events.pubsub;

import com.github.twitch4j.pubsub.domain.ChannelBitsData;
import com.github.twitch4j.pubsub.events.ChannelBitsEvent;
import de.kleindev.twitchbot.external.twitch.events.Event;

public class TwitchChannelBitsEvent extends Event {
    ChannelBitsData channelBitsData;

    public TwitchChannelBitsEvent(ChannelBitsEvent e){
        this.channelBitsData = e.getData();
    }


    public ChannelBitsData getChannelBitsData() {
        return channelBitsData;
    }
}
