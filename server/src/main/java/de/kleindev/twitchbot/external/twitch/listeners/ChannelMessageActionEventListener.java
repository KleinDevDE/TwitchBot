package de.kleindev.twitchbot.external.twitch.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.ChannelMessageActionEvent;
import de.kleindev.twitchbot.logging.Logger;

import java.util.Map;

public class ChannelMessageActionEventListener {

    @EventSubscriber
    public void onEvent(ChannelMessageActionEvent e){
        StringBuilder tags = new StringBuilder();
        StringBuilder badges = new StringBuilder();
        for(Map.Entry<String, String> entry : e.getMessageEvent().getTags().entrySet())
            tags.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        for(Map.Entry<String, String> entry : e.getMessageEvent().getBadges().entrySet())
            badges.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");

        Logger.debug("ChannelMessageActionEvent\n" +
                "---\n" +
                "Channel: " + e.getMessageEvent().getChannel().getName()+"\n" +
                "CommandType: "+e.getMessageEvent().getCommandType()+"\n" +
                "User: " + e.getMessageEvent().getUser().getName()+"\n" +
                "Target: " + e.getMessageEvent().getTargetUser().getName()+"\n" +
                "Tags:\n" + tags.toString() + "\n" +
                "Badges:\n" + badges.toString()+"\n"
        );
    }
}
