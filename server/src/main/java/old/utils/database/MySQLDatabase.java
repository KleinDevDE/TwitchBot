package old.utils.database;

import java.sql.*;

public class MySQLDatabase extends Database {
    public MySQLDatabase(String host, String port, String database, String username, String password, boolean ssl, boolean readOnly, boolean autoReconnect) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection=DriverManager.getConnection(
                    "jdbc:mysql://"+host+":"+port+"/"+database+"?serverTimezone=UTC&autoReconnect="+autoReconnect+"&useSSL="+ssl,username,password);
            connection.setSavepoint(String.valueOf(System.currentTimeMillis()));
            connection.setReadOnly(readOnly);
            sendPings();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
