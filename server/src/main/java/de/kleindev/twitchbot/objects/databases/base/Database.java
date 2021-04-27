package de.kleindev.twitchbot.objects.databases.base;

import de.kleindev.twitchbot.objects.databases.base.objects.Table;

import java.util.Set;

public abstract class Database {
    private String databaseName;
    private Set<Table> tableSet;
}
