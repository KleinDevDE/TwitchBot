package de.kleindev.twitchbot.websocket.packets.auth;


import de.kleindev.twitchbot.websocket.Packet;

public class AuthPacket extends Packet {
    private AuthType authType;

    /**
     * if (authType = LOGOUT)
     * [0] = SessionID
     * <p>
     * if (authType = LOGIN)
     * [0] = SessionID => NULL
     * [1] = Username
     * [2] = Password
     * <p>
     * if (authType = REGISTER)
     * [0] = SessionID => NULL
     * [1] = Username
     * [2] = Password
     * [3] = Email
     * [4] = Twitch_Username
     * [5] = Twitch_Token
     * <p>
     * if (authType = FORGOT_PASSWORD)
     * [0] = SessionID => NULL
     * [1] = Username
     * <p>
     * if (authType = TWOFA_TOKEN)
     * [0] = SessionID
     * [1] = Username
     * [2] = 2FA-Token
     * <p>
     * if (authType = GENERATE_TWOFA)
     * [0] = SessionID
     * [1] = Username
     * <p>
     * if (authType = SUCCESSFUL)
     * [0] = SessionID
     */
    private String[] data;

    public AuthPacket(AuthType authType, String[] data){
        this.authType = authType;
        this.data = data;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public String[] getData() {
        return data;
    }
}
