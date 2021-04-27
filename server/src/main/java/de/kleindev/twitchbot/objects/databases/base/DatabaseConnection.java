package de.kleindev.twitchbot.objects.databases.base;

import de.kleindev.twitchbot.objects.BooleanReturnValue;
import de.kleindev.twitchbot.objects.databases.base.objects.Column;
import de.kleindev.twitchbot.objects.databases.base.objects.KeyPair;
import de.kleindev.twitchbot.objects.databases.base.objects.Row;
import de.kleindev.twitchbot.objects.databases.base.objects.SQLTrigger;

import java.security.SecureRandom;
import java.sql.*;
import java.util.*;

public abstract class DatabaseConnection {
    SecureRandom random = new SecureRandom();
    protected Connection[] connections;
    protected LinkedHashMap<String, Savepoint> savePoints = new LinkedHashMap<>();
    private static Timer timer;

    protected Connection getConnection() {
        return connections[random.nextInt(connections.length)];
    }

    public boolean createTableIfNotExists(String table, Column... columns) {
        StringBuilder sb = new StringBuilder();
        for (Column c : columns) {
            if (sb.toString().equals(""))
                sb.append(c.parseToSQL());
            else sb.append(", ").append(c.parseToSQL());
        }
        try (Statement stm = getConnection().createStatement();){
            return stm.execute("CREATE TABLE IF NOT EXISTS " + table + " (" + sb.toString() + ");");
        } catch (SQLException e) {
            System.out.println("CREATE TABLE IF NOT EXISTS `" + table + "` (" + sb.toString() + ");");
            e.printStackTrace();
        }
        return true;
    }

    public Set<String> getTables(String databaseName){
        Set<String> set = new HashSet<>();
        try(Statement stm = getConnection().createStatement()){
            ResultSet rs = stm.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='"+databaseName+"'");
            while (rs.next())
                set.add(rs.getString("TABLE_NAME"));
            return set;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean insert(String table, KeyPair<?>... keyPairs) {
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        for (KeyPair<?> kp : keyPairs) {
            if (!key.toString().equals(""))
                key.append(", ");
            key.append(kp.getKey());

            if (!value.toString().equals(""))
                value.append(", ");
            value.append("'").append(kp.getValue()).append("'");
        }
        try {
            return getConnection().createStatement().execute("INSERT INTO " + table + " (" + key.toString() + ") VALUES (" + value.toString() + ");");
        } catch (SQLException e) {
            System.out.println("INSERT INTO " + table + " (" + key.toString() + ") VALUES (" + value.toString() + ");");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertOrUpdate(String table, KeyPair<?>... keyPairs) {
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        for (KeyPair<?> kp : keyPairs) {
            if (!key.toString().equals(""))
                key.append(", ");
            key.append(kp.getKey());

            if (!value.toString().equals(""))
                value.append(", ");
            value.append("'").append(kp.getValue()).append("'");
        }
        try {
            return getConnection().createStatement().execute("REPLACE INTO  " + table + " (" + key.toString() + ") VALUES (" + value.toString() + ");");
        } catch (SQLException e) {
            System.out.println("REPLACE INTO  " + table + " (" + key.toString() + ") VALUES (" + value.toString() + ");");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertOrIgnore(String table, KeyPair<?>... keyPairs){
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        for (KeyPair<?> kp : keyPairs) {
            if (!key.toString().equals(""))
                key.append(", ");
            key.append(kp.getKey());

            if (!value.toString().equals(""))
                value.append(", ");
            value.append("'").append(kp.getValue()).append("'");
        }
        try {
            ;
            return getConnection().createStatement().execute("INSERT IGNORE INTO " + table + " (" + key.toString() + ") VALUES (" + value.toString() + ");");
        } catch (SQLException e) {
            System.out.println("INSERT IGNORE INTO " + table + " (" + key.toString() + ") VALUES (" + value.toString() + ");");
            e.printStackTrace();
            return false;
        }
    }

    public String getUserName() {
        try {
            return getConnection().getMetaData().getUserName();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDatabaseProductName() {
        try {
            return getConnection().getMetaData().getDatabaseProductName();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getURL() {
        try {
            return getConnection().getMetaData().getURL();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteRow(String table, KeyPair<?> where) {
        try {
            return getConnection().createStatement().execute("DELETE FROM " + table + " WHERE " + where.getKey() + "='" + where.getValue() + "';");
        } catch (Exception e) {
            System.out.println("DELETE FROM " + table + " WHERE " + where.getKey() + "='" + where.getValue() + "';");
            e.printStackTrace();
            return false;
        }
    }

    public int update(String table, KeyPair<?> where, KeyPair<?> set) {
        try {
            return getConnection().createStatement().executeUpdate("UPDATE INTO " + table + " WHERE " + where.getKey() + "='" + where.getValue() + "' SET " + set.getKey() + "='" + set.getValue() + "';");
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(String table, KeyPair<?> where, KeyPair<?>... keyPairs) {
        try {
            StringBuilder setBuilder = new StringBuilder();
            for (KeyPair<?> kp : keyPairs) {
                if (!setBuilder.toString().equals(""))
                    setBuilder.append(", ");
                setBuilder.append(kp.getKey()).append("=").append("'").append(kp.getValue()).append("'");
            }
            return getConnection().createStatement().executeUpdate("UPDATE " + table + " SET " + setBuilder.toString() + " WHERE " + where.getKey() + "='" + where.getValue() + "';");
        } catch (Exception e) {
            return -1;
        }
    }

    public PreparedStatement getPreparedStatement(String sql) {
        try {
            return getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            return null;
        }
    }

    public Row getRow(String table, KeyPair<?>... searched) {
        try (Statement sm = getConnection().createStatement()) {
            StringBuilder sb = new StringBuilder();
            for (KeyPair<?> kp : searched) {
                if (sb.toString().equals(""))
                    sb.append(kp.getKey()).append("='").append(kp.getValue()).append("'");
                else sb.append(" AND ").append(kp.getKey()).append("='").append(kp.getValue()).append("'");
            }
            ResultSet rs = sm.executeQuery("SELECT * FROM " + table + " WHERE " + sb.toString());
            rs.next();
            return new Row(rs);
        } catch (Exception e) {
            return null;
        }
    }

    public Set<Row> getRows(String table, KeyPair<?>... searched) {
        try (Statement sm = getConnection().createStatement()) {
            Set<Row> rows = new HashSet<>();
            StringBuilder sb = new StringBuilder();
            for (KeyPair<?> kp : searched) {
                if (sb.toString().equals(""))
                    sb.append(" WHERE ").append(kp.getKey()).append("='").append(kp.getValue()).append("'");
                else sb.append(" AND ").append(kp.getKey()).append("='").append(kp.getValue()).append("'");
            }
            sb.append(";");
            ResultSet rs = sm.executeQuery("SELECT * FROM " + table + sb.toString());
            while (rs.next())
                rows.add(new Row(rs));
            return rows;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void restoreLastSavePoint() {
        Iterator<Map.Entry<String, Savepoint>> iterator = savePoints.entrySet().iterator();
        Map.Entry<String, Savepoint> lastElement = null;
        while (iterator.hasNext()) {
            lastElement = iterator.next();
        }
        try {
            assert lastElement != null;
            getConnection().rollback(lastElement.getValue());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSavePoint(String name) {
        try {
            savePoints.put(name, getConnection().setSavepoint());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void restoreSavePoint(String name) {
        try {
            if (savePoints.get(name) != null)
                getConnection().rollback(savePoints.get(name));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeSavePoint(String name) {
        try {
            getConnection().releaseSavepoint(savePoints.get(name));
            savePoints.remove(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public Table cacheTable(String table_name) {
//
//    }
//
//    public Database cacheDatabase() {
//
//    }


    public void closeConnections() throws SQLException {
        for (Connection c : connections)
            c.close();
    }

    protected void ping() {
        final boolean[] shouldSend = new boolean[connections.length];
        for (int i = 0; i < shouldSend.length; i++) //Arrays.fill(shouldSend, true);
            shouldSend[i] = true;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int count = 0;
                for (Connection c : connections) {
                    try (Statement stm = c.createStatement()) {
                        if (!shouldSend[count])
                            return;
                        stm.executeQuery("SELECT 1;");
                    } catch (SQLException e) {
                        shouldSend[count] = false;
                        throw new RuntimeException(e);
                    }
                    count++;
                }
            }
        }, 5000, 10000);
    }

    public BooleanReturnValue exists(String table, KeyPair<?> keyPair) {
        try (Statement sm = getConnection().createStatement()) {
            return new BooleanReturnValue(sm.executeQuery("SELECT EXISTS(SELECT 1 FROM " + table + " WHERE " + keyPair.getKey() + "='" + keyPair.getValue() + "');").next(), null);
        } catch (Exception e) {
            return new BooleanReturnValue(false, e);
        }
    }

    public BooleanReturnValue exists(String table, KeyPair<?>... keyPairs) {
        try (Statement sm = getConnection().createStatement()) {
            StringBuilder sb = new StringBuilder();
            for (KeyPair<?> kp : keyPairs) {
                if (sb.toString().equals(""))
                    sb.append(kp.getKey()).append("='").append(kp.getValue()).append("'");
                else sb.append(" AND ").append(kp.getKey()).append("='").append(kp.getValue()).append("'");
            }
            return new BooleanReturnValue(sm.executeQuery("SELECT 1 FROM " + table + " WHERE " + sb + ";").next(), (Exception) null);
        } catch (Exception var5) {
            return new BooleanReturnValue(false, var5);
        }
    }

    public boolean createTrigger(SQLTrigger trigger) {
        if(triggerExists(trigger.getTriggerName()))
            return false;
        try (Statement stm = getConnection().createStatement()) {
            return stm.execute(trigger.parse());
        } catch (SQLException e) {
            System.out.println(trigger.parse());
            e.printStackTrace();
            return false;
        }
    }

    public boolean triggerExists(String triggerName){
        try (Statement stm = getConnection().createStatement()) {
            ResultSet rs = stm.executeQuery("SHOW TRIGGERS LIKE '" + triggerName + "';"); //TODO SQL Injection!
            return rs.next();
        } catch (SQLException e) {
            System.out.println("SHOW TRIGGERS LIKE '" + triggerName + "';");
            e.printStackTrace();
            return false;
        }
    }

}
