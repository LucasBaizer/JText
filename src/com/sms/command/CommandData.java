package com.sms.command;

import java.util.Arrays;

public class CommandData {
	private String[] arguments;
	private String[] flags;

	public CommandData(String[] args, String[] fs) {
		this.setArguments(args);
		this.setFlags(fs);
	}

	public String[] getArguments() {
		return arguments;
	}

	public void setArguments(String[] arguments) {
		this.arguments = arguments;
	}

	public String[] getFlags() {
		return flags;
	}

	public void setFlags(String[] flags) {
		this.flags = flags;
	}

	public boolean hasFlag(String flag) {
		for (String f : flags) {
			if (f.equals(flag)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "CommandData:[args:" + Arrays.asList(arguments) + "], flags:" + Arrays.asList(flags) + "]";
	}
}