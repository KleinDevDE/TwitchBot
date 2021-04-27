package de.kleindev.twitchbot;

import de.kleindev.twitchbot.logging.Logger;
import de.kleindev.twitchbot.websocket.EndpointManager;
import de.kleindev.twitchbot.websocket.WebSocketManager;
import de.kleindev.twitchbot.websocket.endpoints.AuthEndpoint;
import de.kleindev.twitchbot.websocket.endpoints.PingEndpoint;
import de.kleindev.twitchbot.websocket.exceptions.InvalidWebSocketEndpointClassException;
import org.apache.commons.cli.*;
public class Main {
    private static CommandLine commandLine;

    public static void main(String[] args) throws InvalidWebSocketEndpointClassException {
        Logger.debug("Parsing arguments..");
        parseArgs(args);

        Logger.debug("Registering endpoints...");
        EndpointManager.registerEndpoint(new PingEndpoint());
        EndpointManager.registerEndpoint(new AuthEndpoint());
        Logger.debug("Endpoints registered!");
        Logger.info("Starting websocket...");
        WebSocketManager.startServer("127.0.0.1", 4265);
    }

    private static void parseArgs(String[] args) {
        Options options = new Options();
        Option config = Option.builder()
                .longOpt("config")
                .hasArg(true)
                .required(false)
                .valueSeparator(' ')
                .build();
        Option disableAuthentication = Option.builder()
                .longOpt("disableAuthentication")
                .hasArg(false)
                .required(false)
                .build();
        options.addOption(config);
        options.addOption(disableAuthentication);

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
}
