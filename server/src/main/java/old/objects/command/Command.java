package old.objects.command;

import java.util.HashMap;

public class Command {
    private String command;
    private String message;
    private String commandline;
    private CommandType commandType;
    private CommandAction commandAction;
    private CommandPermission commandPermission;
    private HashMap<String, SubCommand> subCommandHashMap = new HashMap<>();

    public String getCommandline() {
        return commandline;
    }

    public CommandPermission getCommandPermission() {
        return commandPermission;
    }

    public CommandAction getCommandAction() {
        return commandAction;
    }

    public String getCommand() {
        return command;
    }

    public String getMessage() {
        return message;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public void setCommandPermission(CommandPermission commandPermission) {
        this.commandPermission = commandPermission;
    }

    public void setCommandline(String commandline) {
        this.commandline = commandline;
    }

    public void setCommandAction(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, SubCommand> getSubCommands() {
        return subCommandHashMap;
    }

    public void setSubCommands(HashMap<String, SubCommand> subCommandHashMap) {
        this.subCommandHashMap = subCommandHashMap;
    }
}
