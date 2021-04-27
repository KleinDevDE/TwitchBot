package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.FollowEvent;

public class FollowEventListener {
    public static boolean active = false;

    @EventSubscriber
    public void onEvent(FollowEvent e){
//        TwitchBot.getInstance().sendPublicMessage("Danke für dein Follow " + e.getUser().getName()+"!");
//        if (active){
//            TwitchBot.getInstance().follow(Long.valueOf(e.getUser().getId()), false);
//            TwitchBot.getInstance().sendPrivateMessage(e.getUser().getName(), "Wie versprochen kriegst du auch einen Follow zurück :)");
//        }
    }
}
