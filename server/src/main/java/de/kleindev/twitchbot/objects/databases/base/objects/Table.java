package de.kleindev.twitchbot.objects.databases.base.objects;

import de.kleindev.twitchbot.objects.databases.base.DatabaseConnection;

import java.util.Set;

public abstract class Table {
    private DatabaseConnection databaseConnection;

    private String tableName;
    private Set<Row> rows;

    public Table(DatabaseConnection databaseConnection, String table){
        this.tableName = table;
        this.databaseConnection = databaseConnection;
    }

    private void loadCache(){
        rows = databaseConnection.getRows(tableName);
    }

    public Set<Row> getRows() {
        return rows;
    }
}
