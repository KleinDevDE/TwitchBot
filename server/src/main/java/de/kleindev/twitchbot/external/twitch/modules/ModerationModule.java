package de.kleindev.twitchbot.external.twitch.modules;

import de.kleindev.twitchbot.external.twitch.listeners.ChannelModEventListener;
import de.kleindev.twitchbot.external.twitch.listeners.ClearChatEventListener;
import de.kleindev.twitchbot.external.twitch.listeners.UserBanEventListener;
import de.kleindev.twitchbot.external.twitch.listeners.UserTimeoutEventListener;
import de.kleindev.twitchbot.objects.modules.Module;

import java.util.Arrays;
import java.util.List;

public class ModerationModule extends Module {
    @Override
    public List<Class> getNeededListeners() {
        return Arrays.asList(UserBanEventListener.class, ClearChatEventListener.class, UserTimeoutEventListener.class, ChannelModEventListener.class);
    }
}
