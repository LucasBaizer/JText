package com.sms.command;

import com.sms.Console;
import com.sms.SaveData;
import com.sms.command.help.CommandHelp;
import com.sms.pop3.POP3Handler;

public class ExitCommand extends ExecutableCommand {
	@Override
	public boolean isValid(CommandData args) {
		return args.getArguments().length == 1;
	}

	@Override
	public void execute(CommandData args) {
		Console.EXECUTING_COMMANDS = false;
		ConsoleLoadingBar bar = new ConsoleLoadingBar("Exiting... ");
		bar.startAnimation();

		try {
			SaveData.getSaveData().save();
			if (POP3Handler.getClient() != null && POP3Handler.getClient().isConnected())
				POP3Handler.getClient().disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		bar.stopAnimation();

		Runtime.getRuntime().halt(0);
	}

	@Override
	public CommandHelp getHelp() {
		return new CommandHelp("exit", "exit", "Exits the SMS client.");
	}

	@Override
	public ExecutableCommand getParent() {
		return null;
	}
}
