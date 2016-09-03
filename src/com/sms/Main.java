package com.sms;

import com.sms.command.CommandExecutor;
import com.sms.command.Commands;

public class Main {
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				CommandExecutor.executeCommand(Commands.parseArguments("exit"));
			}
		}));

		try {
			SaveData.SaveDataCacheHandler.getSaveDataCacheHandler().initialize();
			TextCacheHandler.initialize();
			CarrierHelper.initializeCustomCarriers();
			ContactHandler.initializeContacts();

			startExecution();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public static void startExecution() {
		while (Console.EXECUTING_COMMANDS) {
			Console.executeNextCommand();
		}
	}
}