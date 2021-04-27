package de.kleindev.twitchbot.objects.exceptions;

public class DatabaseFileInvalidException extends Exception {
    public DatabaseFileInvalidException(String file, String message){
        super("File: " + file + " | " + message);
    }
}
