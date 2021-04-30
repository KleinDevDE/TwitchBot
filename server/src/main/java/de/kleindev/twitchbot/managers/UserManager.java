package de.kleindev.twitchbot.managers;

import com.google.inject.Inject;
import de.kleindev.twitchbot.TwitchBot;
import de.kleindev.twitchbot.objects.LoginResult;
import de.kleindev.twitchbot.objects.databases.base.objects.Row;
import de.kleindev.twitchbot.objects.user.RegisteredUser;
import de.kleindev.twitchbot.objects.user.Session;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserManager {
    private static PreparedStatement preparedStatement_checkCredentials;
    private static PreparedStatement preparedStatement_register;
    private static PreparedStatement preparedStatement_getUserData;
    @Inject private static ArgonManager argonManager;

    static {
        try {
            preparedStatement_checkCredentials = TwitchBot.getInstance().getDatabaseManager().getMySQLConnection("master").prepareStatement("");
            preparedStatement_register = TwitchBot.getInstance().getDatabaseManager().getMySQLConnection("master").prepareStatement("");
            preparedStatement_getUserData = TwitchBot.getInstance().getDatabaseManager().getMySQLConnection("master").prepareStatement("");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @SneakyThrows
    public static LoginResult login(UUID sessionID, String username, String password){
        LoginResult loginResult = new LoginResult();
        preparedStatement_checkCredentials.setString(1, username);
        ResultSet resultSet = preparedStatement_checkCredentials.executeQuery();
        boolean exists = resultSet.next();
        if (!exists){
            loginResult.setErrorCode(1);
            return loginResult;
        }

        String hash = resultSet.getString("strPassword");
        int userID = resultSet.getInt("pkUserID");
        resultSet.close();

        if (!argonManager.verify(hash, password)){
            loginResult.setErrorCode(2);
            return loginResult;
        }


        loginResult.setErrorCode(0);
        RegisteredUser registeredUser = getUser(userID);
        registeredUser.setSessionID(sessionID);
        loginResult.setRegisteredUser(registeredUser);
        return loginResult;
    }

    public static RegisteredUser register(UUID sessionID, String username, String password, String email){

        return null;
    }

    @SneakyThrows
    public static RegisteredUser getUser(int userID){
        preparedStatement_getUserData.setInt(1, userID);
        ResultSet resultSet = preparedStatement_getUserData.executeQuery();
        if (!resultSet.next()){
            return null;
        }
        RegisteredUser registeredUser = new RegisteredUser();
        Session session = new Session();

        registeredUser.setSession(session);
    }

    private static RegisteredUser initUser(final RegisteredUser registeredUser){

        return registeredUser;
    }
}
