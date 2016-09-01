package com.sms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface CacheHandler<T> {
	@SuppressWarnings("unchecked")
	public default T deserializeSave(File cache) throws IOException, ClassNotFoundException {
		try (FileInputStream in = new FileInputStream(cache)) {
			try (ObjectInputStream obj = new ObjectInputStream(in)) {
				return (T) obj.readObject();
			}
		}
	}

	public default void serializeSave(File cache, T object) throws IOException {
		try (FileOutputStream out = new FileOutputStream(cache)) {
			try (ObjectOutputStream obj = new ObjectOutputStream(out)) {
				obj.writeObject(object);
			}
		}
	}
}
