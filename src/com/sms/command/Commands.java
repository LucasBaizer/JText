package com.sms.command;

import java.util.ArrayList;

import com.sms.command.help.HelpCommand;
import com.sms.command.help.HelpSpecificCommand;

public class Commands {
	public static final ExecutableCommand[] COMMANDS = { new HelpCommand(), new HelpSpecificCommand(),
			new LoginCommand(), new SMSOpenCommand(), new LogoutCommand(), new ExitCommand(), new CarrierCommand(),
			new ContactCommand(), new CacheCommand(), new ReadCommand() };

	public static ExecutableCommand[] getCommandsByName(String name) {
		ArrayList<ExecutableCommand> commands = new ArrayList<>();
		for (ExecutableCommand command : COMMANDS) {
			if (command.getHelp().getBase().equals(name)) {
				commands.add(command);
			}
		}
		return commands.toArray(new ExecutableCommand[commands.size()]);
	}

	public static CommandData parseArguments(String command) {
		int index = -1;

		ArrayList<String> args = new ArrayList<String>();
		ArrayList<String> flags = new ArrayList<String>();
		while (index <= command.length() - 1) {
			int start = index + 1;
			int end;

			if (command.charAt(start) == '"') {
				end = command.indexOf('"', start + 1);
				start++;
				index = end + 1;

				args.add(command.substring(start, end));
			} else if (command.charAt(start) == '-') {
				int flagStart = start;
				int flagEnd;
				if (command.indexOf(' ', flagStart) != -1) {
					flagEnd = command.indexOf(' ', flagStart);
				} else {
					flagEnd = command.length();
				}
				index = flagEnd;
				flags.add(command.substring(flagStart, flagEnd));
			} else {
				if (command.indexOf(' ', start) != -1) {
					end = command.indexOf(' ', start);
				} else {
					end = command.length();
				}
				args.add(command.substring(start, end));
				index = end;
			}
		}
		return new CommandData(args.toArray(new String[args.size()]), flags.toArray(new String[flags.size()]));
	}
}