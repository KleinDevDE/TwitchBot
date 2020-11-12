package old.utils.managers;

import old.utils.objects.Argument;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationManager {
    /**
     * Sun property pointing the main class and its arguments.
     * Might not be defined on non Hotspot VM implementations.
     */
    public static final String SUN_JAVA_COMMAND = "sun.java.command";
    public static boolean shouldReboot = false;

    private static List<Argument> arguments = new ArrayList<>();

    /**
     * Restart the current Java application
     * @param runBeforeRestart some custom code to be run before restarting
     * @throws IOException
     */
    public static void restartApplication(Runnable runBeforeRestart) throws IOException {
        shouldReboot = true;
        try {
            String java = System.getProperty("java.home") + "/bin/java";
            List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
            StringBuffer vmArgsOneLine = new StringBuffer();
            for (String arg : vmArguments) {
                if (!arg.contains("-agentlib")) {
                    vmArgsOneLine.append(arg);
                    vmArgsOneLine.append(" ");
                }
            }
            final StringBuffer cmd = new StringBuffer(java + " "+ vmArgsOneLine);

            String[] mainCommand = System.getProperty(SUN_JAVA_COMMAND).split(" ");
            if (mainCommand[0].endsWith(".jar")) {
                cmd.append("-jar " + new File(mainCommand[0]).getPath());
            } else {
                cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
            }
            for (int i = 1; i < mainCommand.length; i++) {
                cmd.append(" ");
                cmd.append(mainCommand[i]);
            }
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        Runtime.getRuntime().exec(cmd.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            if (runBeforeRestart!= null) {
                runBeforeRestart.run();
            }

            //EoBot.getInstance().getPluginManager().callEvent(new BotRebootEvent()); //TODO For plugin architecture, call this event at reboot
            System.exit(0);
        } catch (Exception e) {
            throw new IOException("Error while trying to restart the application", e);
        }
    }

    public static List<Argument> getArguments() {
        return arguments;
    }

    public static void setArguments(List<String> args) {
        args = args.stream().map(String::toLowerCase).collect(Collectors.toList());
        for(Argument argument : Argument.values()){
            if (args.contains(argument.getArg())) {
                arguments.add(argument);
                args.remove(argument.getArg());
            }
        }

        for(String s : args){
            System.out.print("Argument \""+s+"\" is not valid!");
        }
    }

    public static boolean hasArgument(Argument argument){
        return arguments.contains(argument);
    }

    public static String getCheckSum(){
        return "";
    }
}
