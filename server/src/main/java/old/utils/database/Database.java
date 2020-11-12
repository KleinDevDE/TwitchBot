package old.utils.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Database {
    protected Connection connection;
    private HashMap<String, PreparedStatement> preparedStatementHashMap = new HashMap<>();

    public void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement preparedStatement(String sql){
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SQLWarning getWarnings(){
        try {
            return connection.getWarnings();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void sendPings(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1;");
                    preparedStatement.executeQuery();
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 60000);
    }

    protected Connection getConnection(){
        return this.connection;
    }

    public void registerPreparedStatement(String key, String sql){
        try {
            preparedStatementHashMap.put(key, connection.prepareStatement(sql));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public PreparedStatement getPreparedStatement(String key){
        return preparedStatementHashMap.get(key);
    }

}
