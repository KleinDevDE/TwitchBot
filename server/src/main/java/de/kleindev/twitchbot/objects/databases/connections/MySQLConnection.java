package de.kleindev.twitchbot.objects.databases.connections;

import de.kleindev.twitchbot.objects.databases.base.DatabaseConnection;

import java.sql.*;

public class MySQLConnection extends DatabaseConnection {

    public MySQLConnection(String host, int port, String database, String username, String password, boolean ssl, boolean autoReconnect, boolean readOnly) {
        try {
            connections = new Connection[10];
            Class.forName("com.mysql.cj.jdbc.Driver");
            for(int i = 0; i < connections.length; i++){
                connections[i] = DriverManager.getConnection(
                        "jdbc:mysql://" + host + ":" + port + "/" + database + "?serverTimezone=UTC&autoReconnect=" + autoReconnect + "&useSSL=" + ssl+"&charset=utf8", username, password);
                connections[i].setReadOnly(readOnly);
            }
            savePoints.put("connect", connections[0].setSavepoint(String.valueOf(System.currentTimeMillis())));
            ping();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }


}
