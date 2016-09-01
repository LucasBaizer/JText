package com.sms.command.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.sms.command.CommandData;
import com.sms.command.Commands;
import com.sms.command.ExecutableCommand;

public class HelpSpecificCommand extends ExecutableCommand {
	@Override
	public boolean isValid(CommandData args) {
		return args.getArguments().length == 2;
	}

	@Override
	public void execute(CommandData args) {
		ExecutableCommand[] commands = Commands.getCommandsByName(args.getArguments()[1]);
		if (commands.length == 0) {
			System.out.println("Unknown command: " + args.getArguments()[1]);
			System.out.println("Type \"help\" for a list of valid commands.");
			return;
		}
		ExecutableCommand command = commands[0];
		System.out.println("Command base: " + command.getHelp().getBase());
		System.out.println("Description: " + command.getHelp().getBaseData());
		System.out.println("Usage: " + command.getHelp().getFormat());
		System.out.println("Arguments:");
		if (command.getHelp().getArgumentData().size() > 0) {
			writeArgumentHelp(command.getHelp().getArgumentData(), 0, 1);
		} else {
			System.out.println("\tNo valid arguments.");
		}
		System.out.println("Flags:");
		if (command.getHelp().getFlagData().size() > 0) {
			List<String> sorted = asSortedList(command.getHelp().getFlagData().keySet());
			int longestFlag = getLongestString(sorted);
			for (String flag : sorted) {
				System.out.println("\t" + flag + ": " + repeatChar(' ', longestFlag - flag.length())
						+ command.getHelp().getFlagData().get(flag));
			}
		} else {
			System.out.println("\tNo valid flags.");
		}
	}

	private void writeArgumentHelp(List<ArgumentHelp> argumentHelp, int longest, int depth) {
		int longestArgument = getLongestString(argumentHelp, longest);

		for (ArgumentHelp argument : argumentHelp) {
			System.out.println(
					repeatChar('\t', depth) + argument.getArgumentName() + ": " + argument.getArgumentDescription());

			if (argument.getChildren() != null && argument.getChildren().length > 0) {
				writeArgumentHelp(Arrays.asList(argument.getChildren()), longestArgument, depth + 1);
			}
		}
	}

	private <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
		List<T> list = new ArrayList<T>(c);
		Collections.sort(list);
		return list;
	}

	private String repeatChar(char c, int t) {
		String result = "";
		for (int i = 0; i < t; i++) {
			result += c;
		}
		return result;
	}

	private int getLongestString(List<ArgumentHelp> collection, int l) {
		int longest = 0;
		for (ArgumentHelp v : collection) {
			if (v.getArgumentName().length() > longest) {
				longest = v.getArgumentName().length();
			}
		}
		return longest;
	}

	private int getLongestString(Collection<String> collection) {
		int longest = 0;
		for (String v : collection) {
			if (v.length() > longest) {
				longest = v.length();
			}
		}
		return longest;
	}

	@Override
	public CommandHelp getHelp() {
		CommandHelp help = new CommandHelp("man <command>", "man",
				"Gets detailed information about a specific command.");
		help.addArgumentData("command", "The command to get information about.");
		return help;
	}

	@Override
	public ExecutableCommand getParent() {
		return null;
	}
}