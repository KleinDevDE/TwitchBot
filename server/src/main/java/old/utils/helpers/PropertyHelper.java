package old.utils.helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertyHelper {

	public static void fill(Properties properties, InputStream in) {
		if(in == null) return;
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
			String data = null;
			while((data = reader.readLine()) != null) {
				if(data.startsWith("#")) continue;
				if(!data.contains("=")) continue;
				String[] arr = data.split("=");
				String key = arr[0];
				StringBuilder val = new StringBuilder();
				for(int i = 1; i < arr.length; i++) {
					val.append("=").append(arr[i]);
				}
				String value = val.length() > 0 ? val.substring(1) : "";
				properties.put(key, value);
				properties.setProperty(key, value);
				
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
}
