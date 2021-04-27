package de.kleindev.twitchbot.objects.databases.connections;

import de.kleindev.twitchbot.objects.databases.base.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLite3Connection extends DatabaseConnection {
    public SQLite3Connection(String filename, String password){
        connections = new Connection[1];
        try {
            Class.forName("org.sqlite.JDBC");
            connections[0] = DriverManager.getConnection("jdbc:sqlite:"+filename, "", password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
