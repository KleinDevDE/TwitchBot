package de.kleindev.twitchbot.objects.databases.base.objects;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class Row {
	private HashMap<String, String> entries = new HashMap<>();

	public Row(ResultSet rs) {
		try {
			if (!rs.next()){
				return;
			}
			ResultSetMetaData rsm = rs.getMetaData();
			int columnCount = rsm.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String name = rsm.getColumnName(i);
				String s = rs.getString(i);
				entries.put(name, s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getValue(String column) {
		return entries.get(column);
	}
	
	public HashMap<String, String> getEntries() {
		return entries;
	}
}
