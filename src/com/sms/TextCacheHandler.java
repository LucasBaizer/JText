package com.sms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TextCacheHandler implements CacheHandler<TextCache[]> {
	private static final File CACHE_FILE = new File(System.getProperty("user.dir") + "\\texts.cache");

	private static ArrayList<TextCache> textCaches = new ArrayList<>();
	private static TextCacheHandler instance = new TextCacheHandler();

	public static void initialize() throws ClassNotFoundException, IOException {
		if (!CACHE_FILE.exists()) {
			CACHE_FILE.createNewFile();
			instance.serializeSave(CACHE_FILE, textCaches.toArray(new TextCache[0]));
			return;
		}

		textCaches = new ArrayList<TextCache>(Arrays.asList(instance.deserializeSave(CACHE_FILE)));
	}

	public static TextCache[] getTextCaches(Filter<TextCache> filter) {
		ArrayList<TextCache> passing = new ArrayList<>();
		for (TextCache cache : textCaches) {
			if (filter.filter(cache)) {
				passing.add(cache);
			}
		}
		return passing.toArray(new TextCache[passing.size()]);
	}

	public static TextCache getTextCache(String cacheID) {
		for (TextCache cache : textCaches) {
			if (cache.getCacheID().equals(cacheID)) {
				return cache;
			}
		}
		return null;
	}

	public static boolean containsCache(String cacheID) {
		return getTextCache(cacheID) != null;
	}

	public static void cacheText(String msg, String cacheID, String from) {
		if (containsCache(cacheID)) {
			getTextCache(cacheID).addText(from, msg);
		} else {
			TextCache newCache = new TextCache(from);
			newCache.setCacheID(cacheID);
			newCache.addText(from, msg);
			textCaches.add(newCache);
		}

		try {
			instance.serializeSave(CACHE_FILE, textCaches.toArray(new TextCache[textCaches.size()]));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
