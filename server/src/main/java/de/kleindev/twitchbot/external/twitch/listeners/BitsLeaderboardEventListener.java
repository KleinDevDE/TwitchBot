package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.pubsub.events.BitsLeaderboardEvent;
import com.google.gson.Gson;
import de.kleindev.twitchbot.logging.Logger;

public class BitsLeaderboardEventListener{

    @EventSubscriber
    public void onEvent(BitsLeaderboardEvent e){
        Logger.debug("BitsLeaderboardEvent\n" +
                new Gson().toJson(e.getData()));
    }
}
