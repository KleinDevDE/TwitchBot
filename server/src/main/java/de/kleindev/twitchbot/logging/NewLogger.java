package de.kleindev.twitchbot.logging;

import de.kleindev.twitchbot.helpers.DiscardingQueue;
import de.kleindev.twitchbot.helpers.StackTraceHelper;
import de.kleindev.twitchbot.utils.ExceptionHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

public class NewLogger {
    static BufferedWriter output;
    static SimpleDateFormat FORMAT = new SimpleDateFormat("[HH:mm:ss]");
    static DiscardingQueue<String> lastLogs = new DiscardingQueue<>(30);

    static {
        try {
            File logFolder = new File("logs");
            File latest_log = new File(logFolder, "latest.log");

            if (!logFolder.exists()) {
                logFolder.mkdirs();
            }

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
            } else {
                latest_log.createNewFile();
            }
            output = new BufferedWriter(new FileWriter(new File(logFolder, "latest.log"), true));
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }
    }

    private static void _log(String message, boolean newLine) {
        try {
            lastLogs.add(message);
            output.write(message);
            if (newLine){
                output.newLine();
            }
            output.flush();
        } catch (IOException e) {
            Thread.currentThread().getThreadGroup().uncaughtException(Thread.currentThread(), e);
        }
    }

    private static void print(String message) {
        System.out.println(message);
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
        return !System.getProperty("os.name").toLowerCase().contains("win");
    }

    public static DiscardingQueue<String> getLastLogs() {
        return lastLogs;
    }
}
