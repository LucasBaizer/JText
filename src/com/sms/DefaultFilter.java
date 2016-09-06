package com.sms;

public class DefaultFilter<T> implements Filter<T> {
	@Override
	public boolean filter(T obj) {
		return true;
	}
}
