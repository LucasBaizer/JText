package com.sms.command.help;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandHelp {
	private String format;
	private String base;
	private String baseData;
	private ArrayList<ArgumentHelp> argumentData = new ArrayList<>();
	private HashMap<String, String> flagData = new HashMap<>();

	public CommandHelp(String format, String base, String baseData) {
		this.setFormat(format);
		this.base = base;
		this.baseData = baseData;
	}

	public ArrayList<ArgumentHelp> getArgumentData() {
		return argumentData;
	}

	public HashMap<String, String> getFlagData() {
		return flagData;
	}

	public void addArgumentData(String arg, String d) {
		this.argumentData.add(new ArgumentHelp(arg, d));
	}

	public void addArgumentData(ArgumentHelp arg) {
		this.argumentData.add(arg);
	}

	public void addFlagData(String flag, String d) {
		this.flagData.put(flag, d);
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getBaseData() {
		return baseData;
	}

	public void setBaseData(String baseData) {
		this.baseData = baseData;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}