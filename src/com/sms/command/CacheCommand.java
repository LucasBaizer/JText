package com.sms.command;

import java.io.IOException;

import com.sms.SaveData;
import com.sms.command.help.CommandHelp;

public class CacheCommand extends ExecutableCommand {
	@Override
	public boolean isValid(CommandData args) {
		return args.getArguments().length == 1;
	}

	@Override
	public void execute(CommandData args) {
		try {
			SaveData.getSaveData().save();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		System.out.println("Saved cache.");
	}

	@Override
	public CommandHelp getHelp() {
		return new CommandHelp("cache", "cache", "Caches unsaved changes immediately.");
	}

	@Override
	public ExecutableCommand getParent() {
		return null;
	}
}
