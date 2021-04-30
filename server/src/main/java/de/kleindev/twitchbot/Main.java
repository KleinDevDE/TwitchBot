package de.kleindev.twitchbot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.kleindev.twitchbot.configuration.TwitchBotConfiguration;
import de.kleindev.twitchbot.logging.Logger;
import de.kleindev.twitchbot.managers.ArgonManager;
import de.kleindev.twitchbot.managers.DatabaseManager;
import de.kleindev.twitchbot.managers.EventManager;
import de.kleindev.twitchbot.objects.databases.base.DatabaseConnection;
import de.kleindev.twitchbot.objects.databases.base.objects.Column;
import de.kleindev.twitchbot.objects.databases.base.objects.ColumnType;
import de.kleindev.twitchbot.websocket.EndpointManager;
import de.kleindev.twitchbot.websocket.WebSocketManager;
import de.kleindev.twitchbot.websocket.endpoints.AuthEndpoint;
import de.kleindev.twitchbot.websocket.endpoints.PingEndpoint;
import de.kleindev.twitchbot.websocket.exceptions.InvalidWebSocketEndpointClassException;
import de.mkammerer.argon2.Argon2Factory;
import org.apache.commons.cli.*;

import java.io.File;

public class Main {
    private static CommandLine commandLine;

    public static void main(String[] args) throws InvalidWebSocketEndpointClassException {
        Logger.debug("Parsing arguments..");
        parseArgs(args);

        // Setup dependency injections
        Injector injector = Guice.createInjector(
                new EventManager(),
                new TwitchBotConfiguration(new File(commandLine.hasOption("config") ? commandLine.getOptionValue("config") : "config.yml")),
                new DatabaseManager(new File("connections")),
                new ArgonManager(Argon2Factory.create())
        );

        Logger.debug("Registering endpoints...");
        EndpointManager.registerEndpoint(new PingEndpoint());
        EndpointManager.registerEndpoint(new AuthEndpoint());
        Logger.debug("Endpoints registered!");
        Logger.info("Starting websocket...");
        WebSocketManager.startServer("127.0.0.1", 4265);
        Logger.info("TwitchBot started!");
    }

    private static void parseArgs(String[] args) {
        Options options = new Options();
        Option config = Option.builder()
                .longOpt("config")
                .hasArg(true)
                .required(false)
                .valueSeparator(' ')
                .build();
        options.addOption(config);

        CommandLineParser parser = new DefaultParser();
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println("Commandline parsing failed. Reason: " + e.getMessage());
        }
    }

    public static CommandLine getCommandLine() {
        return commandLine;
    }

    private static void createTablesIfNotExists(){
        DatabaseConnection databaseConnection = null;

        databaseConnection.createTableIfNotExists(
                "users",
                new Column("pkUserID", ColumnType.INT).setPrimary(true).setLength(11),
                new Column("strUsername", ColumnType.VARCHAR).setUnique(true).setLength(36),
                new Column("strEmail", ColumnType.INT).setUnique(true).setLength(998),
                new Column("strPassword", ColumnType.TEXT).setLength(11)
        );

        databaseConnection.createTableIfNotExists(
                "user_data",
                new Column("intAgron2Iterations", ColumnType.INT).setUnique(true).setLength(11),
                new Column("strTwitchOAuthBotToken", ColumnType.TEXT),
                new Column("strTwitchOAuthStreamerToken", ColumnType.TEXT),
                new Column("strTwitchCommandPrefix", ColumnType.VARCHAR).setLength(10),
                new Column("strTwitchChatBotNickColor", ColumnType.VARCHAR).setLength(7),
                new Column("arrEnabledModules", ColumnType.TEXT),
                new Column("strStreamerChannelName", ColumnType.TINYTEXT),
                new Column("intTwitchStreamerChannelD", ColumnType.TINYTEXT),
                new Column("strDiscordBotToken", ColumnType.TEXT)
        );

        databaseConnection.createTableIfNotExists(
                "user_session",
                new Column("fkUserID", ColumnType.INT).setUnique(true).setLength(11),
                new Column("uuidSessionID", ColumnType.VARCHAR).setUnique(true).setLength(50),
                new Column("datCreated", ColumnType.TIMESTAMP),
                new Column("datExpiresAt", ColumnType.TIMESTAMP)
        );
    }
}
