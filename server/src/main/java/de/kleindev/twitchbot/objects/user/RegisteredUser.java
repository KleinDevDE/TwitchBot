package de.kleindev.twitchbot.objects.user;

import de.kleindev.twitchbot.external.discord.DiscordAPI;
import de.kleindev.twitchbot.external.discord.DiscordModules;
import de.kleindev.twitchbot.external.email.EmailAPI;
import de.kleindev.twitchbot.external.twitch.TwitchAPI;
import de.kleindev.twitchbot.external.twitch.TwitchModules;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisteredUser {
    private Session session;
    private Long userID;

    private int argon2Iterations;
    boolean twitchAPIClientEnabled;
    boolean discordAPIClientEnabled;
    boolean mailAPIClientEnabled;
    List<TwitchModules> twitchModules;
    List<DiscordModules> discordModules;
    TwitchAPI twitchAPI;
    DiscordAPI discordAPI;
    EmailAPI emailAPI;
}
