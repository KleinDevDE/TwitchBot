package de.kleindev.twitchbot.objects;

public class ScheduledMessage {
    private String title;
    private long duration;
    private String message;
    private long lastExecution;

    /**
     *
     * @param message
     * @param duration in seconds
     */
    public ScheduledMessage(String title, String message, long duration){
        this.duration = duration;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public long getDuration() {
        return duration;
    }

    public long getLastExecution() {
        return lastExecution;
    }

    public void setLastExecution(long lastExecution) {
        this.lastExecution = lastExecution;
    }
}
