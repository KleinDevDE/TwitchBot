package de.kleindev.twitchbot.external.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TwitchConfig {
    private OAuth2Credential oAuth2Credential;
    private long streamerID;
    private String streamerChannelID;
    private String streamerChannelName;
    private String commandPrefix;
    private String chatNickColor;
    private List<Module> modules;
}
