package de.kleindev.twitchbot.external.twitch.modules;

import de.kleindev.twitchbot.external.twitch.listeners.ChannelPointsRedemptionEventListener;
import de.kleindev.twitchbot.external.twitch.listeners.RedemptionStatusUpdateEventListener;
import de.kleindev.twitchbot.objects.modules.Module;

import java.util.Arrays;
import java.util.List;

public class ChannelPointsModule extends Module {
    @Override
    public List<Class> getNeededListeners() {
        return Arrays.asList(ChannelPointsRedemptionEventListener.class, RedemptionStatusUpdateEventListener.class);
    }
}
