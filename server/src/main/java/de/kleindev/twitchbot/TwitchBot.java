package de.kleindev.twitchbot;

import de.kleindev.twitchbot.managers.DatabaseManager;
import de.kleindev.twitchbot.managers.EventManager;
import lombok.Getter;

@Getter
public class TwitchBot {
    private static TwitchBot INSTANCE;
    private EventManager eventManager;
    private DatabaseManager databaseManager;

    public TwitchBot(){
        TwitchBot.INSTANCE = this;

    }

    public void startBot(){

    }

    public static TwitchBot getInstance() {
        return TwitchBot.INSTANCE;
    }
}
