package de.kleindev.twitchbot.websocket.packets.auth;

public enum AuthType {
    LOGOUT,
    LOGIN,
    REGISTER,
    FORGOT_PASSWORD,
    TWOFA_TOKEN,
    GENERATE_TWOFA
}
