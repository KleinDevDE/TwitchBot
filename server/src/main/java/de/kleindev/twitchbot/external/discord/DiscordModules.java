package de.kleindev.twitchbot.external.discord;

import lombok.Getter;

public enum DiscordModules {
    DEFAULT_COMMANDS(0),
    CUSTOM_COMMANDS(1);


    @Getter
    private int id;

    DiscordModules(int id){
        this.id = id;
    }
}
