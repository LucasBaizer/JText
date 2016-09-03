package com.sms.command;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.mail.MessagingException;

import com.sms.Carrier;
import com.sms.CarrierHelper;
import com.sms.Contact;
import com.sms.ContactHelper;
import com.sms.SMTPHandler;
import com.sms.SaveData;
import com.sms.Text;
import com.sms.TextCache;
import com.sms.TextCacheHandler;
import com.sms.command.help.CommandHelp;
import com.sms.pop3.POP3Handler;

public class ReadCommand extends ExecutableCommand {
	@Override
	public boolean isValid(CommandData args) {
		return (args.getArguments().length == 2 && args.getArguments()[1].equals("*"))
				|| args.getArguments().length == 3 || args.getArguments().length == 4;
	}

	@Override
	public void execute(CommandData args) {
		if (!SMTPHandler.isLoggedIn()) {
			if (SaveData.isNewData()) {
				System.out.println("Please log in to your SMTP/POP3 services' account first.");
				return;
			} else {
				System.out.println();
				CommandExecutor.executeCommand(Commands.parseArguments("login -l"));
				System.out.println();
			}
		}

		int amount = 0;
		if (!args.getArguments()[1].equals("*")) {
			try {
				amount = Integer.parseInt(args.getArguments()[2]);
			} catch (NumberFormatException e) {
				System.out.println("Invalid amount of texts to read.");
				return;
			}
		}
		int finalAmount = amount;
		try {
			if (args.getArguments().length == 4) {
				Carrier carrier = CarrierHelper.getAnyCarrier(args.getArguments()[3]);
				if (carrier != null) {
					read(args.getArguments()[1], args.getArguments()[1], carrier, amount);
				} else {
					System.out.println("Unknown carrier alias: \"" + args.getArguments()[2] + "\".");
					return;
				}
			} else {
				if (args.getArguments()[1].equals("*")) {
					for (Contact contact : ContactHelper.getContacts()) {
						read(contact.getContactName(), contact.getPhoneNumber(), contact.getCarrier(), finalAmount);
						System.out.println();
					}
				} else {
					Contact contact = ContactHelper.getContact(args.getArguments()[1]);
					if (contact != null) {
						read(contact.getContactName(), contact.getPhoneNumber(), contact.getCarrier(), amount);
					} else {
						System.out.println("No contact found with name \"" + args.getArguments()[1] + "\".");
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void read(String name, String number, Carrier carrier, int amount) throws MessagingException, IOException {
		String readingFrom = number + "@" + carrier.getAddress();

		System.out.println("Messages with " + name + ": ");
		TextCache cache = TextCacheHandler.getTextCache(readingFrom);
		if (cache != null) {
			for (Text txt : cache.getTexts()) {
				System.out.println("\t" + (txt.getSenderName().equals("You") ? "You" : name) + ": "
						+ txt.getText().replaceAll("\n", ""));
			}
		} else {
			System.out.println("\tNo stored messages.");
		}

		List<String> messages = POP3Handler.getClient().getMessages(readingFrom, amount);
		if (messages != null) {
			Collections.reverse(messages);

			if (messages.size() > 0) {
				System.out.println();
				for (String msg : messages) {
					System.out.println("\t" + msg.replaceAll("\n", ""));
					TextCacheHandler.cacheText(msg, readingFrom, readingFrom);
				}
			} else {
				System.out.println("\tNo new messages.");
			}
		}
	}

	@Override
	public CommandHelp getHelp() {
		CommandHelp help = new CommandHelp("read <target> <amount> {target=#:<carrier>}", "read",
				"Reads a certain amount of texts from a specified number or contact.");
		help.addArgumentData("target",
				"The target device, either a phone number or a contact name (specified by the present flag).");
		help.addArgumentData("amount",
				"An integer specifying how many messages to read in reverse chronological order. Not required if target is a wildcard.");
		help.addArgumentData("carrier",
				"Specifies the target devices' carrier. Only required when using a phone number.");
		return help;
	}

	@Override
	public ExecutableCommand getParent() {
		return null;
	}
}
