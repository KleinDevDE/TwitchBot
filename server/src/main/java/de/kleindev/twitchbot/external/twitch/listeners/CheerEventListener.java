package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.CheerEvent;
import de.kleindev.twitchbot.logging.Logger;

public class CheerEventListener {

    @EventSubscriber
    public void onEvent(CheerEvent e){
        Logger.debug("CheerEvent \n" +
                "User: " + e.getUser().getName() + "\n" +
                "Bits: " + e.getBits()+"\n" +
                "Message: " + e.getMessage());
//        if (e.getBits() == 1)
//            TwitchBot.getInstance().sendPublicMessage("@"+e.getUser().getName() + " hat 1 Bit gespendet!");
//        else TwitchBot.getInstance().sendPublicMessage("@"+e.getUser().getName() + " hat " + e.getBits() + " Bits gespendet!");
    }
}
