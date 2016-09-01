package com.sms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TextCache implements Serializable {
	private static final long serialVersionUID = 7131518707721561182L;

	private String cacheID;
	private ArrayList<Text> cache = new ArrayList<>();

	public TextCache(String cacheID) {
		this.cacheID = cacheID;
	}

	public List<Text> getTexts() {
		return cache;
	}

	public void addText(String source, String txt) {
		cache.add(new Text(source, txt));
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof TextCache) {
			return ((TextCache) o).cacheID.equals(this.cacheID);
		}
		return false;
	}

	public String getCacheID() {
		return cacheID;
	}

	public void setCacheID(String cacheID) {
		this.cacheID = cacheID;
	}
}