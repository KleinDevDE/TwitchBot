package de.kleindev.twitchbot.objects.databases.base;


import de.kleindev.twitchbot.configuration.file.FileConfiguration;
import de.kleindev.twitchbot.objects.exceptions.DatabaseFileInvalidException;

public class DBConnectionFile {
    private FileConfiguration cfg;

    public DBConnectionFile(FileConfiguration cfg) throws DatabaseFileInvalidException {
        this.cfg = cfg;
        checkFile();
    }

    public String getString(String key){
        return cfg.getString(key);
    }

    private void checkFile() throws DatabaseFileInvalidException {
        if(cfg.get("mysql") == null && cfg.get("sqlite") == null)
            throw new DatabaseFileInvalidException(cfg.getName(), "Neither mysql nor sqlite was set!");
        if(cfg.get("mysql") != null && cfg.get("sqlite") != null){
            throw new DatabaseFileInvalidException(cfg.getName(), "MySQL and SQLite cannot be set simultaneously!");
        }
        if(cfg.get("mysql") != null) {
            if (cfg.get("mysql.credentials") == null)
                throw new DatabaseFileInvalidException(cfg.getName(), "No credentials was set!");
            if (cfg.get("mysql.credentials.host") == null)
                throw new DatabaseFileInvalidException(cfg.getName(), "No host was set!");
            if (cfg.get("mysql.credentials.port") == null)
                throw new DatabaseFileInvalidException(cfg.getName(), "No port was set!");
            //TODO check if port is integer
            if (cfg.get("mysql.credentials.database") == null)
                throw new DatabaseFileInvalidException(cfg.getName(), "No database was set!");
            if (cfg.get("mysql.credentials.username") == null)
                throw new DatabaseFileInvalidException(cfg.getName(), "No username was set!");
            if (cfg.get("mysql.credentials.password") == null)
                throw new DatabaseFileInvalidException(cfg.getName(), "No password was set!");
            if (cfg.get("mysql.options") == null)
                throw new DatabaseFileInvalidException(cfg.getName(), "No options was set!");
            if (cfg.get("mysql.options.ssl") == null)
                throw new DatabaseFileInvalidException(cfg.getName(), "No option \"ssl\" was set!");
            //TODO  Check if is boolean
            if (cfg.get("mysql.options.autoReconnect") == null)
                throw new DatabaseFileInvalidException(cfg.getName(), "No option \"autoReconnect\" was set!");
            //TODO  Check if is boolean
            if (cfg.get("mysql.options.readOnly") == null)
                throw new DatabaseFileInvalidException(cfg.getName(), "No option \"readOnly\" was set!");
            //TODO  Check if is boolean
        } else if (cfg.get("sqlite") != null){
            if (cfg.get("sqlite.file") == null)
                throw new DatabaseFileInvalidException(cfg.getName(), "No file was set!");
            if (cfg.get("sqlite.password") == null)
                throw new DatabaseFileInvalidException(cfg.getName(), "No password was set!");
        }
    }
}
