package de.kleindev.twitchbot.external.twitch.modules;

import de.kleindev.twitchbot.external.twitch.listeners.BitsLeaderboardEventListener;
import de.kleindev.twitchbot.external.twitch.listeners.ChannelBitsEventListener;
import de.kleindev.twitchbot.external.twitch.listeners.CheerEventListener;
import de.kleindev.twitchbot.objects.modules.Module;

import java.util.Arrays;
import java.util.List;

public class BitsModule extends Module {
    @Override
    public List<Class> getNeededListeners() {
        return Arrays.asList(ChannelBitsEventListener.class, BitsLeaderboardEventListener.class, CheerEventListener.class);
    }
}
