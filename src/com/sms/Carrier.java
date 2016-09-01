package com.sms;

import java.io.Serializable;

public class Carrier implements Serializable, Comparable<Carrier> {
	private static final long serialVersionUID = -7628606719683552173L;
	private String name;
	private String alias;
	private String address;

	public Carrier(String name, String alias, String address) {
		this.name = name.trim();
		this.alias = alias.trim();
		this.address = address.trim().replaceAll("@", "");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int compareTo(Carrier o) {
		return this.name.compareTo(o.name);
	}
}