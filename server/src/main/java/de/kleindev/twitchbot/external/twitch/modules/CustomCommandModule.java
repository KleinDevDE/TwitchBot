package de.kleindev.twitchbot.external.twitch.modules;

import de.kleindev.twitchbot.external.twitch.listeners.ChannelMessageEventListener;
import de.kleindev.twitchbot.objects.modules.Module;

import java.util.Collections;
import java.util.List;

public class CustomCommandModule extends Module {
    @Override
    public List<Class> getNeededListeners() {
        return Collections.singletonList(ChannelMessageEventListener.class);
    }
}
