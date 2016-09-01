package com.sms.command;

import com.sms.command.help.CommandHelp;

public abstract class ExecutableCommand implements Comparable<ExecutableCommand> {
	public abstract boolean isValid(CommandData args);

	public abstract void execute(CommandData args);

	public abstract CommandHelp getHelp();

	public abstract ExecutableCommand getParent();

	@Override
	public int compareTo(ExecutableCommand other) {
		return this.getHelp().getBase().compareTo(other.getHelp().getBase());
	}
}