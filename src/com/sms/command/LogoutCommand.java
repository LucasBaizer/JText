package com.sms.command;

import com.sms.SMTPHandler;
import com.sms.command.help.CommandHelp;
import com.sms.pop3.POP3Handler;

public class LogoutCommand extends ExecutableCommand {
	@Override
	public boolean isValid(CommandData args) {
		return args.getArguments().length == 1;
	}

	@Override
	public void execute(CommandData args) {
		if (SMTPHandler.isLoggedIn()) {
			SMTPHandler.setSMTPSession(null);
			if (POP3Handler.getClient() != null && POP3Handler.getClient().isConnected()) {
				try {
					POP3Handler.getClient().disconnect();
					POP3Handler.setClient(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("Logged out.");
			SMTPHandler.setCurrentEmailAddress(null);
			SMTPHandler.setCurrentPassword(null);
		} else {
			System.out.println("You are not logged in to any account!");
		}
	}

	@Override
	public CommandHelp getHelp() {
		return new CommandHelp("logout", "logout", "Logs out of the current account.");
	}

	@Override
	public ExecutableCommand getParent() {
		return null;
	}
}
