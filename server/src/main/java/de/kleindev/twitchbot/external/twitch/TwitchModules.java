package de.kleindev.twitchbot.external.twitch;

import lombok.Getter;

public enum TwitchModules {
    DEFAULT_COMMANDS(0),
    CUSTOM_COMMANDS(1),
    CHANNEL_POINTS(3),
    SCHEDULED_MESSAGES(4),
    BIRTHDAYS(5);


    @Getter private int id;

    TwitchModules(int id){
        this.id = id;
    }
}
