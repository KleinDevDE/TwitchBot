package de.kleindev.twitchbot;

import com.google.inject.Inject;
import de.kleindev.twitchbot.configuration.TwitchBotConfiguration;
import de.kleindev.twitchbot.managers.DatabaseManager;
import de.kleindev.twitchbot.managers.EventManager;
import lombok.Getter;

@Getter
public class TwitchBot {
    private static TwitchBot INSTANCE;
    @Inject private EventManager eventManager;
    @Inject private DatabaseManager databaseManager;
    @Inject private TwitchBotConfiguration twitchBotConfiguration;

    public TwitchBot(){
        TwitchBot.INSTANCE = this;
    }
    public static TwitchBot getInstance() {
        return TwitchBot.INSTANCE;
    }
}
