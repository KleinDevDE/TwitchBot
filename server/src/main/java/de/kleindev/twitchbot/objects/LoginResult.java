package de.kleindev.twitchbot.objects;

import de.kleindev.twitchbot.objects.user.RegisteredUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResult {
    /**
     * 0 = nothing
     * 1 = account does not exists
     * 2 = wrong password
     * 3 = account deactivated
     * 4 = unknown error
     */
    private int errorCode;
    private RegisteredUser registeredUser;
}
