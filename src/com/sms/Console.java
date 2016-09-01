package com.sms;

import java.util.NoSuchElementException;
import java.util.Scanner;

import com.sms.command.CommandData;
import com.sms.command.CommandExecutor;
import com.sms.command.Commands;

public class Console {
	private static final Scanner in;
	public static boolean EXECUTING_COMMANDS = true;

	static {
		in = new Scanner(System.in);
	}

	public static void executeNextCommand() {
		if (EXECUTING_COMMANDS) {
			try {
				System.out.print("JavaSMS/Terminal>");
				CommandData parsed = Commands.parseArguments(in.nextLine().trim());
				CommandExecutor.executeCommand(parsed);
			} catch (StringIndexOutOfBoundsException e) {
				return;
			} catch (NoSuchElementException e) {
				System.out.println();
				EXECUTING_COMMANDS = false;
			}
		}
	}

	public static String writeAndRead(String out) {
		try {
			System.out.print(out);
			return in.nextLine().trim();
		} catch (StringIndexOutOfBoundsException e) {
			return null;
		} catch (NoSuchElementException e) {
			System.out.println();
			EXECUTING_COMMANDS = false;
			return null;
		}
	}

	public static String writeAndReadPassword(String out) {
		try {
			System.out.print(out);
			return System.console() == null ? in.nextLine().trim() : new String(System.console().readPassword()).trim();
		} catch (StringIndexOutOfBoundsException e) {
			return null;
		} catch (NoSuchElementException e) {
			System.out.println();
			EXECUTING_COMMANDS = false;
			return null;
		}
	}
}