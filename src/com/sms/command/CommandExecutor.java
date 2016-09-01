package com.sms.command;

public class CommandExecutor {
	public static void executeCommand(CommandData data) {
		String base = data.getArguments()[0].toLowerCase();

		ExecutableCommand command = null;
		boolean foundCommand = false;
		boolean failure = false;
		for (ExecutableCommand exec : Commands.COMMANDS) {
			if (exec.getHelp().getBase().equals(base)) {
				command = exec;
				failure = runIfValid(exec, data);
				foundCommand = true;
				break;
			}
		}

		if (!foundCommand) {
			System.out.println("Unknown command: " + data.getArguments()[0]);
			System.out.println("Type \"help\" for a list of valid commands.");
			return;
		}

		if (failure) {
			System.out.println("Invalid usage of command.");
			System.out.println("Usage: " + command.getHelp().getFormat());
			System.out.println("Type \"man " + base + "\" for more information on how to use the command.");
		}
	}

	private static boolean runIfValid(ExecutableCommand exec, CommandData data) {
		if (exec.isValid(data)) {
			exec.execute(data);
			return false;
		}
		return true;
	}
}