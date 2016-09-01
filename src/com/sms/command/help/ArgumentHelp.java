package com.sms.command.help;

import java.io.Serializable;

public class ArgumentHelp implements Serializable {
	private static final long serialVersionUID = -440023556227328573L;
	private ArgumentHelp[] children;
	private String argumentName;
	private String argumentDescription;

	public ArgumentHelp(String argumentName, String argumentDescription, ArgumentHelp... children) {
		this.children = children;
		this.argumentName = argumentName;
		this.argumentDescription = argumentDescription;
	}

	public ArgumentHelp[] getChildren() {
		return children;
	}

	public void setChildren(ArgumentHelp... children) {
		this.children = children;
	}

	public String getArgumentName() {
		return argumentName;
	}

	public void setArgumentName(String argumentName) {
		this.argumentName = argumentName;
	}

	public String getArgumentDescription() {
		return argumentDescription;
	}

	public void setArgumentDescription(String argumentDescription) {
		this.argumentDescription = argumentDescription;
	}

	@Override
	public String toString() {
		return argumentName + ": " + argumentDescription;
	}
}