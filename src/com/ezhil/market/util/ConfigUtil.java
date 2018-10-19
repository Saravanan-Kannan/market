package com.ezhil.market.util;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class ConfigUtil {

	private static Map<String, String> propMap = new HashMap<>();

	public void initalizeMap() {
		if (propMap != null && propMap.size() > 0) {
			return;
		}
		try {
			FileReader reader = new FileReader(
					"\\MyProjects\\src\\com\\ezhil\\market\\util\\load.properties");

			Properties p = new Properties();
			p.load(reader);
			Set<Entry<Object, Object>> set = p.entrySet();

			Iterator<Entry<Object, Object>> itr = set.iterator();
			while (itr.hasNext()) {
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) itr.next();
				propMap.put(entry.getKey().toString(), entry.getValue().toString());
				System.out.println(entry.getKey() + " = " + entry.getValue());
			}

		} catch (Exception e) {
			System.out.println("Properties file not loaded successfully");
			e.printStackTrace();
		}
	}

	public String getPropMap(String key) {
		return propMap.get(key);
	}


}
