package de.kleindev.twitchbot.managers;

import com.github.twitch4j.modules.IModule;
import com.google.inject.Binder;
import com.google.inject.Module;
import de.kleindev.twitchbot.configuration.file.YamlConfiguration;
import de.kleindev.twitchbot.logging.LogType;
import de.kleindev.twitchbot.logging.Logger;
import de.kleindev.twitchbot.objects.databases.base.DatabaseConnection;
import de.kleindev.twitchbot.objects.databases.connections.MySQLConnection;
import de.kleindev.twitchbot.objects.databases.connections.SQLite3Connection;
import de.kleindev.twitchbot.objects.databases.base.DBConnectionFile;
import de.kleindev.twitchbot.objects.exceptions.DatabaseFileInvalidException;
import de.kleindev.twitchbot.utils.ExceptionHandler;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

public class DatabaseManager implements Module {
    File connectionsFolder;
    private static HashMap<String, DatabaseConnection> connectionHashMap = new HashMap<>();

    public DatabaseManager(File connectionsFolder) {
        Logger.debug("Initializing databases...");
        this.connectionsFolder = connectionsFolder;
        if (!connectionsFolder.exists()) {
            Logger.debug("Connectionsfolder does not exists, creating one...");
            connectionsFolder.mkdirs();
            Logger.log(LogType.RAW_WITHOUT_TIME, "     [OK]");
        }
        init();
    }

    public MySQLConnection getMySQLConnection(String name) {
        if (connectionHashMap.containsKey(name))
            if (connectionHashMap.get(name) instanceof MySQLConnection)
                return (MySQLConnection) connectionHashMap.get(name);
        try {
            throw new Exception("No connection found by name \"" + name + "\"");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SQLite3Connection getSQLite3Connection(String name) {
        if (connectionHashMap.containsKey(name))
            if (connectionHashMap.get(name) instanceof SQLite3Connection)
                return (SQLite3Connection) connectionHashMap.get(name);
        try {
            throw new Exception("No connection found by name \"" + name + "\"");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void init() {
        for (File f : Objects.requireNonNull(connectionsFolder.listFiles())) {
            try {
                DBConnectionFile dbConnectionFile = new DBConnectionFile(YamlConfiguration.loadConfiguration(f));
                if (dbConnectionFile.getString("mysql") != null) {
                    System.out.println("Registering mysql connection \"" + f.getName().replace(".yml", "") + "\" with database \"" + dbConnectionFile.getString("mysql.credentials.database") + "\"");
                    connectionHashMap.put(f.getName().replace(".yml", ""), new MySQLConnection(
                            dbConnectionFile.getString("mysql.credentials.host"),
                            Integer.parseInt(dbConnectionFile.getString("mysql.credentials.port")),
                            dbConnectionFile.getString("mysql.credentials.database"),
                            dbConnectionFile.getString("mysql.credentials.username"),
                            dbConnectionFile.getString("mysql.credentials.password"),
                            Boolean.parseBoolean(dbConnectionFile.getString("mysql.options.ssl")),
                            Boolean.parseBoolean(dbConnectionFile.getString("mysql.options.autoReconnect")),
                            Boolean.parseBoolean(dbConnectionFile.getString("mysql.options.readOnly"))
                    ));
                } else if (dbConnectionFile.getString("sqlite") != null) {
                    connectionHashMap.put(f.getName().replace(".yml", ""), new SQLite3Connection(dbConnectionFile.getString("sqlite.file"), dbConnectionFile.getString("sqlite.password")));
                } else System.err.println("No valid type found on connection file \"" + f.getName() + "\"!");
            } catch (DatabaseFileInvalidException e) {
                ExceptionHandler.handle(e);
            }
        }
        Logger.log("Databases initialized!");
    }

    private void checkFile(){
        //TODO Check if file is valid
    }

    public void closeAll() throws SQLException {
        for (DatabaseConnection db : connectionHashMap.values())
            db.closeConnections();
    }

    @Override
    public void configure(Binder binder) {

    }
}
