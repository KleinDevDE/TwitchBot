package de.kleindev.twitchbot.utils;

import de.kleindev.twitchbot.Main;
import de.kleindev.twitchbot.logging.LogType;
import de.kleindev.twitchbot.logging.Logger;
import de.kleindev.twitchbot.managers.ApplicationManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static StackTraceElement[] lastStackTrace;
    private static long lastException = 0L;
    private static int count = 0;
    public static AtomicBoolean hasError = new AtomicBoolean(false);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handle(e);
    }

    public static void handle(Throwable e) {
        hasError.set(true);

        //If there are 10+ exceptions in 3 seconds, the bot will reboot
        // If the reboot failed, bot will shutdown
        count++;
        if (count >= 10) {
            if (lastException != 0L && (System.currentTimeMillis() - lastException) <= 3000) {
                Logger.log(LogType.FATAL, "Too many exceptions! Forcing reboot..");
                try {
                    ApplicationManager.restartApplication(null);
                    return;
                } catch (IOException ex) {
                    Logger.log(LogType.FATAL, "Failed to reboot! Forcing shutdown..");
                    System.exit(0);
                }
            }
        } else {
            lastException = System.currentTimeMillis();
            count = 0;
        }


        // Skip if last Stacktrace is the same
        if (lastStackTrace != null && Arrays.equals(lastStackTrace, e.getStackTrace()))
            return;
        lastStackTrace = e.getStackTrace();

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        StringBuilder lastLogs = new StringBuilder();
        StringBuilder out = new StringBuilder();
        StringBuilder args = new StringBuilder();
        StringBuilder jvm_args = new StringBuilder();
        int size = Logger.getLastLogs().size();
        for (String s : Logger.getLastLogs()) {
            size--;
            if (size > 10)
                continue;
            lastLogs.append(s).append("\n");
        }
        for (String arg : Main.getCommandLine().getArgs())
            args.append(arg).append(" ");
        for (String s : ManagementFactory.getRuntimeMXBean().getInputArguments())
            jvm_args.append(s).append(" ");

        Logger.error("An Exception has thrown!\n"
                + "Working directory   " + new File("./").getAbsolutePath() + "\n"
                + "JVM parameters      " + jvm_args + "\n"
                + "Start arguments     " + args + "\n"
                + "Thread ID           " + String.valueOf(Thread.currentThread().getId()) + "\n"
                + "-----\n"
                + sw.toString()
        );
    }
}