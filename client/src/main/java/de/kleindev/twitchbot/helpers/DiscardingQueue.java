package de.kleindev.twitchbot.helpers;

import java.util.ArrayDeque;
import java.util.Collection;

public class DiscardingQueue<T> extends ArrayDeque<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int capacity;
	
	public DiscardingQueue(int capacity) {
		this.capacity = capacity;
	}
	
	@Override
	public boolean add(T e) {
		boolean b = super.add(e);
		while(size() > capacity) {
			poll();
		}
		return b;
	}
	
	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean b = super.addAll(c);
		while(size() > capacity) {
			poll();
		}
		return b;
	}
	
}
