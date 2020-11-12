package old.objects.command;

public class SubCommand {
    private String command;
    private String message;
    private String commandline;
    private CommandType commandType;
    private CommandAction commandAction;
    private CommandPermission commandPermission;


    public CommandType getCommandType() {
        return commandType;
    }

    public String getMessage() {
        return message;
    }

    public String getCommand() {
        return command;
    }

    public CommandAction getCommandAction() {
        return commandAction;
    }

    public CommandPermission getCommandPermission() {
        return commandPermission;
    }

    public String getCommandline() {
        return commandline;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setCommandAction(CommandAction commandAction) {
        this.commandAction = commandAction;
    }

    public void setCommandline(String commandline) {
        this.commandline = commandline;
    }

    public void setCommandPermission(CommandPermission commandPermission) {
        this.commandPermission = commandPermission;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }
}
