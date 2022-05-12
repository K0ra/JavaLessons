package main.java.com.luxoft.jva.collections.bankapp.utils;

import java.util.HashMap;
import java.util.Map;

public class Params {
	private Map<String, String> parametersMap = new HashMap<>();

	public Params(String[] parameters) {
		for (String param : parameters) {
			parametersMap.put(param.split("=")[0], param.split("=")[1]);
		}
	}

	public String get(String key) {
		return parametersMap.get(key);
	}


	public String getOrDefault(String key, String def) {
		return parametersMap.getOrDefault(key, def);
	}
}
