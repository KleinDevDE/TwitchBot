package de.kleindev.twitchbot.objects.modules;

import java.util.List;

public abstract class Module {

    public abstract List<Class> getNeededListeners();
}
