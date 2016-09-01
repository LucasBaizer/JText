package com.sms.command.help;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sms.command.CommandData;
import com.sms.command.Commands;
import com.sms.command.ExecutableCommand;

public class HelpCommand extends ExecutableCommand {
	@Override
	public boolean isValid(CommandData args) {
		return args.getArguments().length == 1;
	}

	@Override
	public void execute(CommandData args) {
		List<ExecutableCommand> list = Arrays.asList(Commands.COMMANDS);
		Collections.sort(list);
		for (ExecutableCommand command : list) {
			System.out.println(command.getHelp().getBase() + " - " + command.getHelp().getBaseData());
		}
		System.out.println();
		System.out.println("Type \"man <command>\" to get help with a specfic command.");
	}

	@Override
	public CommandHelp getHelp() {
		CommandHelp help = new CommandHelp("help", "help", "Gets a list of information about each command.");
		return help;
	}

	@Override
	public ExecutableCommand getParent() {
		return null;
	}
}