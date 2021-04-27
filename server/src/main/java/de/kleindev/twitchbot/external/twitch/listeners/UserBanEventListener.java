package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.UserBanEvent;

public class UserBanEventListener {

    @EventSubscriber
    public void onEvent(UserBanEvent e){
//        TwitchBot.getInstance().sendPublicMessage("Der User \""+e.getUser().getName()+"\" wurde aufgrund von " + e.getReason() + " gebannt.");
    }
}
