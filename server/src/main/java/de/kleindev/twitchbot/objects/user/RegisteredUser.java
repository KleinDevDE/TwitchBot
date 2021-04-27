package de.kleindev.twitchbot.objects.user;

import de.kleindev.twitchbot.external.discord.DiscordAPI;
import de.kleindev.twitchbot.external.email.EmailAPI;
import de.kleindev.twitchbot.external.twitch.TwitchAPI;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class RegisteredUser {
    private UUID userID;

    boolean twitchAPIClientEnabled;
    boolean discordAPIClientEnabled;
    boolean mailAPIClientEnabled;
    List<Module> modules;
    TwitchAPI twitchAPI;
    DiscordAPI discordAPI;
    EmailAPI emailAPI;

    public RegisteredUser() {
        //... = ...
    }

    public void setupClient() {
        //...
    }
}
