package de.kleindev.twitchbot.objects.databases.base.objects;

public class KeyPair <T>{
	private String key;
	private T value;
	
	public KeyPair(String key, T value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public T getValue() {
		return value;
	}
}
