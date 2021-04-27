package de.kleindev.twitchbot.external.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwitchConfig {
    private OAuth2Credential oAuth2Credential;
    private long steamerID;
    private String streamerChannelID;
    private String streamerChannelName;
}
