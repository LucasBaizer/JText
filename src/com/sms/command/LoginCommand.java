package com.sms.command;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.sms.Console;
import com.sms.SMTPHandler;
import com.sms.SaveData;
import com.sms.command.help.CommandHelp;
import com.sms.pop3.POP3Handler;

public class LoginCommand extends ExecutableCommand {
	@Override
	public boolean isValid(CommandData args) {
		return args.getArguments().length == 1;
	}

	@Override
	public void execute(CommandData args) {
		if (args.hasFlag("--status")) {
			System.out.println("Logged in to SMTP: " + (SMTPHandler.isLoggedIn() ? "yes" : "no"));
			if (SMTPHandler.isLoggedIn()) {
				System.out.println("Logged in to SMTP address: " + SMTPHandler.getCurrentEmailAddress() + " at "
						+ SMTPHandler.getSMTPServer() + ":" + SMTPHandler.getSMTPPort());
			}
			System.out.println("Logged in to POP3: " + (POP3Handler.isLoggedIn() ? "yes" : "no"));
			if (POP3Handler.isLoggedIn()) {
				System.out.println("Logged in to POP3 address: " + POP3Handler.getClient().getUsername() + " at "
						+ POP3Handler.getPOP3Server() + ":" + POP3Handler.getPOP3Port());
			}
			return;
		}

		String smtpEmail = "";
		String smtpPassword = "";
		String pop3Email = "";
		String pop3Password = "";
		String smtpServer = null;
		int smtpPort = -1;
		String pop3Server = null;
		int pop3Port = -1;

		if (args.hasFlag("-l")) {
			smtpEmail = SaveData.getSaveData().getSmtpEmail();
			smtpPassword = SaveData.getSaveData().getSmtpPassword();
			pop3Email = SaveData.getSaveData().getPop3Email();
			pop3Password = SaveData.getSaveData().getPop3Password();
			smtpServer = SaveData.getSaveData().getPreferredSmtpServer().split(Pattern.quote(":"))[0];
			smtpPort = Integer.parseInt(SaveData.getSaveData().getPreferredSmtpServer().split(Pattern.quote(":"))[1]);
			pop3Server = SaveData.getSaveData().getPreferredPop3Server().split(Pattern.quote(":"))[0];
			pop3Port = Integer.parseInt(SaveData.getSaveData().getPreferredPop3Server().split(Pattern.quote(":"))[1]);
		} else {
			smtpEmail = Console.writeAndRead("SMTP email address: ").toLowerCase();
			smtpPassword = Console.writeAndReadPassword("SMTP password: ");
			pop3Email = Console.writeAndRead("POP3 email address: ").toLowerCase();
			pop3Password = Console.writeAndReadPassword("POP3 password: ");
			String smtp = Console.writeAndRead("SMTP server and port: ").toLowerCase();
			smtpServer = smtp.split(Pattern.quote(":"))[0];
			smtpPort = Integer.parseInt(smtp.split(Pattern.quote(":"))[1]);

			String pop3 = Console.writeAndRead("POP3 server and port: ").toLowerCase();
			pop3Server = pop3.split(Pattern.quote(":"))[0];
			pop3Port = Integer.parseInt(pop3.split(Pattern.quote(":"))[1]);
		}

		if (!args.hasFlag("-c")) {
			if (smtpEmail == null && smtpPassword == null) {
				if (args.hasFlag("-l")) {
					System.out.println("No cached SMTP credentials.");
					return;
				}
			}
			if (pop3Email == null && pop3Password == null) {
				if (args.hasFlag("-l")) {
					System.out.println("No cached POP3 credentials.");
					return;
				}
			}
			if (smtpServer == null && smtpPort == -1) {
				if (args.hasFlag("-l")) {
					System.out.println("No cached SMTP server.");
					return;
				}
			}
			if (pop3Server == null && pop3Port == -1) {
				if (args.hasFlag("-l")) {
					System.out.println("No cached POP3 server.");
					return;
				}
			}
			SaveData.getSaveData().setPreferredPop3Server(pop3Server + ":" + pop3Port);
			SaveData.getSaveData().setPreferredSmtpServer(smtpServer + ":" + smtpPort);
			SaveData.getSaveData().setSmtpEmail(smtpEmail);
			SaveData.getSaveData().setSmtpPassword(smtpPassword);
			SaveData.getSaveData().setPop3Email(pop3Email);
			SaveData.getSaveData().setPop3Password(pop3Password);

			try {
				SaveData.getSaveData().save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ConsoleLoadingBar loading = new ConsoleLoadingBar("Attemping SMTP session for " + smtpEmail + "...");
		loading.startAnimation();

		Properties smtp = new Properties();
		smtp.put("mail.smtp.auth", "true");
		smtp.put("mail.smtp.starttls.enable", "true");

		final String email0 = smtpEmail;
		final String password0 = smtpPassword;
		Session smtpSession = Session.getInstance(smtp, new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email0, password0);
			}
		});
		SMTPHandler.setSMTPServer(smtpServer);
		SMTPHandler.setSMTPPort(smtpPort);
		SMTPHandler.setCurrentEmailAddress(smtpEmail);
		SMTPHandler.setCurrentPassword(smtpPassword);
		SMTPHandler.setSMTPSession(smtpSession);
		loading.stopAnimation();

		System.out.println(" SMTP session created.");

		loading = new ConsoleLoadingBar("Attemping POP3 session for " + pop3Email + "...");
		loading.startAnimation();

		POP3Handler.setPOP3Server(pop3Server);
		POP3Handler.setPOP3Port(pop3Port);
		POP3Handler.createClient(pop3Email, pop3Password, loading);

		loading.stopAnimation();
		System.out.println(" POP3 session created.");
	}

	@Override
	public ExecutableCommand getParent() {
		return null;
	}

	@Override
	public CommandHelp getHelp() {
		CommandHelp help = new CommandHelp("login", "login",
				"Logs in to the Gmail SMTP server with specified credentials.");
		help.addFlagData("-l", "Logs in using cached credentials.");
		help.addFlagData("-c", "Prevents credentials from being cached.");
		help.addFlagData("--status", "Gets the current status of the user's login.");
		return help;
	}
}