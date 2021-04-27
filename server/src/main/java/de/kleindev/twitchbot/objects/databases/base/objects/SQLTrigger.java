package de.kleindev.twitchbot.objects.databases.base.objects;

public class SQLTrigger {
    private String trigger_name;
    private String table_name;
    private String prefix;
    private String sql;

    public SQLTrigger(String trigger_name, String table_name){
        this.trigger_name = trigger_name;
        this.table_name = table_name;
    }

    /**
     * @param beforeOrAfter        - 1=before, 2=after
     * @param insertUpdateOrDelete - 1=insert, 2=update, 3=delete
     * @return SQLTrigger itself
     */
    public SQLTrigger setActivation(int beforeOrAfter, int insertUpdateOrDelete) {

        try {
            if (beforeOrAfter > 2 || beforeOrAfter < 1)
                throw new Exception("Wrong int (beforeOrAfter) set! Couldn't determine which type you want.");
            if(insertUpdateOrDelete > 3 || insertUpdateOrDelete < 1)
                throw new Exception("Wrong int (insertUpdateOrDelete) set! Couldn't determine which type you want.");

            prefix = (beforeOrAfter == 1 ? "BEFORE " : "AFTER ");
            if (insertUpdateOrDelete == 1)
                prefix += "INSERT";
            else if (insertUpdateOrDelete == 2)
                prefix += "UPDATE";
            else prefix += "DELETE";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public SQLTrigger setSQL(String sql){
        this.sql = sql;
        return this;
    }

    public String parse(){
        //TODO BIG BANG BUG, if you don't know what I mean, Just reload the server
        return "CREATE TRIGGER `"+ trigger_name + "` " + prefix + " ON `" + table_name + "` FOR EACH ROW BEGIN " + sql + " END";
    }

    public String getTriggerName() {
        return trigger_name;
    }
}
