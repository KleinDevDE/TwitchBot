package old.utils.managers;

import old.utils.database.Database;
import old.utils.exceptions.DatabaseAlreadyRegisteredException;

import java.util.*;

public class DatabaseManager {
    private static final Random rand = new Random();
    private static HashMap<String, List<Database>> databases = new HashMap<>();

    public static void registerDatabaseConnection(String key, Database database) throws DatabaseAlreadyRegisteredException {
        List<Database> databaseList = new ArrayList<>();
        if (databases.containsKey(key)){
            throw new DatabaseAlreadyRegisteredException("Database \""+key+"\" is already registered!");
        }



        databases.put(key, databaseList);
    }

    public static Database getDatabase(String key){
        List<Database> databaseList = databases.get(key);
        if (databaseList.size() == 1)
            return databaseList.get(0);
        else {
           return databaseList.get(rand.nextInt(databaseList.size()));
        }
    }

    public static void closeDatabases(){
        for(Map.Entry<String, List<Database>> entry : databases.entrySet()){
            for(Database d : entry.getValue())
                d.disconnect();
        }
    }
}
