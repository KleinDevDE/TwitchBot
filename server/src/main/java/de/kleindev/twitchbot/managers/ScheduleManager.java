package de.kleindev.twitchbot.managers;

import de.kleindev.twitchbot.TwitchBot;
import de.kleindev.twitchbot.objects.ScheduledMessage;
import de.kleindev.twitchbot.utils.ExceptionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduleManager {
    private Timer timer = new Timer();
    private List<ScheduledMessage> scheduledMessages = new ArrayList<>();

    public ScheduleManager(){
        try {
            ResultSet resultSet = TwitchBot.getInstance().getDatabaseManager().getSQLite3Connection("data").getPreparedStatement("SELECT * FROM scheduledmessages;").executeQuery();
            while (resultSet.next()){

            }
        } catch (SQLException e) {
            ExceptionHandler.handle(e);
        }
    }

    public void registerScheduledMessage(ScheduledMessage scheduledMessage){
        TwitchBot.getInstance().getDatabaseManager().getSQLite3Connection("data").getPreparedStatement("INSERT INTO scheduledmessages VALUES ();");
        scheduledMessages.add(scheduledMessage);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                TwitchBot.getInstance().sendPublicMessage(scheduledMessage.getMessage());
            }
        }, (scheduledMessage.getDuration()*1000)/2, scheduledMessage.getDuration()*1000);
    }

    public void stop(){
        timer.cancel();
    }

    public List<ScheduledMessage> getScheduledMessages() {
        return scheduledMessages;
    }
}
