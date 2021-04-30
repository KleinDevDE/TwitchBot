package de.kleindev.twitchbot.objects.user;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Session {
    private long createdAt;
    private long expiringAt;
    private UUID sessionID;
}
