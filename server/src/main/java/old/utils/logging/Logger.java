package old.utils.logging;

import old.utils.ExceptionHandler;
import old.utils.helpers.StackTraceHelper;
import old.utils.managers.ApplicationManager;
import old.utils.objects.Argument;
import old.utils.objects.DiscardingQueue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    static BufferedWriter output;
    static SimpleDateFormat FORMAT = new SimpleDateFormat("[HH:mm:ss]");
    static DiscardingQueue<String> lastLogs = new DiscardingQueue<>(10);

    public Logger(File logFolder) {
        try {
            if (!logFolder.exists())
                logFolder.mkdirs();

            File latest_log = new File(logFolder, "latest.log");
            if (latest_log.exists()) {
                BasicFileAttributes attr = Files.readAttributes(latest_log.toPath(), BasicFileAttributes.class);
                String date = new SimpleDateFormat("dd.MM.yyyy_HH-mm-ss").format(attr.creationTime().toMillis());
                int count = 0;
                File f = new File(logFolder, date + ".log");
                while (f.exists()) {
                    f = new File(logFolder, date + "-" + ++count + ".log");
                }
                Files.move(latest_log.toPath(), f.toPath());
                new File(logFolder, "latest.log").createNewFile();
                output = new BufferedWriter(new FileWriter(new File(logFolder, "latest.log"), true));
            } else {
                latest_log.createNewFile();
                output = new BufferedWriter(new FileWriter(new File(logFolder, "latest.log"), true));
            }
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }
    }

    public static void log(String messageFormat, Object... args) {
        log(String.format(messageFormat, args));
    }

    public static void log(LogType logType, String messageFormat, Object... args) {
        log(logType, String.format(messageFormat, args));
    }

    public static void log(String message) {
        __log(LogType.INFO, message, true, true);
    }

    public static void log(String message, boolean print) {
        __log(LogType.INFO, message, print, true);
    }

    public static void log(String message, boolean print, boolean lineBreak) {
        __log(LogType.INFO, message, print, lineBreak);
    }

    public static void
    log(LogType logType, String message) {
        __log(logType, message, true, true);
    }

    public static void log(LogType logType, String message, boolean print) {
        __log(logType, message, print, true);
    }
    public static void log(LogType logType, String message, boolean print, boolean lineBreak) {
        __log(logType, message, print, lineBreak);
    }

    private static void __log(LogType logType, String message, boolean print, boolean lineBreak) {
//        String prePrefix = TwitchBot.getInstance().getPluginManager().getPluginNameByPackage(getPackage());
//        if (!prePrefix.equals(""))
//            prePrefix = "[" + prePrefix + "] ";
//        String prefix = prePrefix;
        String prefix = "";
        if(ApplicationManager.getArguments().contains(Argument.NO_CONSOLE))
            print = false;
        if (logType == LogType.INFO) {
            if (print)
                print(FORMAT.format(new Date()) + TextColor.CYAN + " INFO" +TextColor.RESET + "     | " + prefix + message + TextColor.RESET, lineBreak);
            _log(FORMAT.format(new Date()) + " INFO     | " + prefix + message, lineBreak);
        } else if (logType == LogType.ERROR) {
            if (print) {
                if (isColored())
                    printError(FORMAT.format(new Date()) + TextColor.RED + " ERROR"+TextColor.RESET+"    | " + TextColor.RED + prefix + message + TextColor.RESET, lineBreak);
                else printError(FORMAT.format(new Date()) + " ERROR    | " + prefix + message, lineBreak);
            }
            _log(FORMAT.format(new Date()) + " ERROR    | " + prefix + message, lineBreak);
        } else if (logType == LogType.UPDATE) {
            if (print) {
                if (isColored())
                    print(FORMAT.format(new Date()) + TextColor.PURPLE + " UPDATE"+TextColor.RESET+"   | " + TextColor.PURPLE +  prefix + message + TextColor.RESET, lineBreak);
                else print(FORMAT.format(new Date()) + " UPDATE   | " + prefix + message, lineBreak);
            }
            _log(FORMAT.format(new Date()) + " UPDATE   | " + prefix + message, lineBreak);
        } else if (logType == LogType.FATAL) {
            if (print) {
                if (isColored())
                    print(FORMAT.format(new Date()) + TextColor.RED_BACKGROUND + " !FATAL!  | " + prefix + message + TextColor.RESET, lineBreak);
                else print(FORMAT.format(new Date()) + " !FATAL!  | " + prefix + message, lineBreak);
            }
            _log(FORMAT.format(new Date()) + " !FATAL!  | " + prefix + message, lineBreak);
        } else if (logType == LogType.DEBUG) {
            //TODO check if debugging is enabled for specific plugin
            if (print) {
                if (isColored())
                    print(FORMAT.format(new Date()) + TextColor.GREEN + " DEBUG"+TextColor.RESET+"    | " + TextColor.GREEN + prefix + message + TextColor.RESET, lineBreak);
                else print(FORMAT.format(new Date()) + " DEBUG    | " + prefix + message, lineBreak);
            }
            _log(FORMAT.format(new Date()) + " DEBUG    | " + prefix + message, lineBreak);
        } else if (logType == LogType.RAW) {
            if (print)
                print(FORMAT.format(new Date()) + " " + prefix + message, lineBreak);
            _log(FORMAT.format(new Date()) + " " + prefix + message, lineBreak);
        }else if (logType == LogType.RAW_WITHOUT_TIME) {
            if (print)
                print(prefix + message, lineBreak);
            _log(prefix + message, lineBreak);
        } else if (logType == LogType.WARNING) {
            if (print) {
                if (isColored())
                    print(FORMAT.format(new Date()) + TextColor.YELLOW + " WARNING"+TextColor.RESET+"  | " + TextColor.YELLOW + prefix + message + TextColor.RESET, lineBreak);
                else print(FORMAT.format(new Date()) + " WARNING  | " + prefix + message, lineBreak);
            }
            _log(FORMAT.format(new Date()) + " WARNING  | " + prefix + message, lineBreak);
        }

    }

    private static void _log(String message, boolean lineBreak) {
        try {
            lastLogs.add(message);
            output.write(message);
            if(lineBreak)
                output.newLine();
            output.flush();
        } catch (IOException e) {
            Thread.currentThread().getThreadGroup().uncaughtException(Thread.currentThread(), e);
        }
    }

    private static void print(String message, boolean lineBreak) {
        if(lineBreak)
            System.out.println(message);
        else System.out.print(message);
    }

    private static void printError(String message, boolean lineBreak) {
        if(lineBreak)
            System.err.println(message);
        else System.err.print(message);
    }

    //TODO +1 or -1
    private static String getPackage() {
        //int count = -1;
        //List<StackTraceHelper.ConstructedStackTraceElement> constructedStackTraceElement = StackTraceHelper.getElements(Thread.currentThread().getStackTrace());
        //for(StackTraceHelper.ConstructedStackTraceElement c : constructedStackTraceElement)
            //_log(count++ + " | " + c.packageName);
        return StackTraceHelper.getElements(Thread.currentThread().getStackTrace()).get(4).packageName;
    }

    private static boolean isColored() {
        //TODO check colored parameter
        return ApplicationManager.hasArgument(Argument.NO_COLOR);
        //return !System.getProperty("os.name").toLowerCase().contains("win");
    }

    public static DiscardingQueue<String> getLastLogs() {
        return lastLogs;
    }
}
