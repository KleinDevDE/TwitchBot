package de.kleindev.twitchbot.configuration;

import com.google.inject.Binder;
import com.google.inject.Module;

import java.io.File;

public class TwitchBotConfiguration extends Config implements Module {
    private File file;

    @Override
    public File getFile() {
        return file;
    }

    @ConfigPath(path = "application.logType", description = "LogTypes: NORMAL, DEBUG, TRACE")
    public String logType = "NORMAL";
//    @ConfigPath(path = "application.pluginsFolder")
//    public String pluginsFolder = "plugins";
    @ConfigPath(path = "application.logsFolder")
    public String logsFolder = "logs";
//    @ConfigPath(path = "application.connectionsFolder")
//    public String connectionsFolder = "connections";

    public TwitchBotConfiguration(File file){
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        this.file = file;
        load();
    }

    @Override
    public void configure(Binder binder) {

    }
}
