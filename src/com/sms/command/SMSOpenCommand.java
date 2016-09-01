package com.sms.command;

import java.util.List;

import com.sms.CarrierHelper;
import com.sms.Console;
import com.sms.Contact;
import com.sms.ContactHelper;
import com.sms.Main;
import com.sms.SMTPHandler;
import com.sms.SaveData;
import com.sms.Text;
import com.sms.TextCache;
import com.sms.TextCacheHandler;
import com.sms.Value;
import com.sms.command.help.CommandHelp;
import com.sms.pop3.POP3Handler;

public class SMSOpenCommand extends ExecutableCommand {
	@Override
	public boolean isValid(CommandData args) {
		return args.getArguments().length == 2 || args.getArguments().length == 3;
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

		String targetPhone = "";
		String contact = null;
		if (args.getArguments().length == 3) {
			if (CarrierHelper.carrierAdded(args.getArguments()[2])) {
				targetPhone = args.getArguments()[1] + "@"
						+ (CarrierHelper.getAnyCarrier(args.getArguments()[2]).getAddress());
				contact = args.getArguments()[2];
			} else {
				System.out.println("Unknown carrier alias: \"" + args.getArguments()[2] + "\".");
				return;
			}
		} else if (args.getArguments().length == 2) {
			Contact c = ContactHelper.getContact(args.getArguments()[1]);
			if (c != null) {
				targetPhone = c.getPhoneNumber() + "@" + c.getCarrier().getAddress();
				contact = c.getContactName();
			} else {
				System.out.println("No contact found with name \"" + args.getArguments()[1] + "\".");
				return;
			}
		}

		Console.EXECUTING_COMMANDS = false;
		System.out.println("Opening SMS connection with " + targetPhone + "...");
		System.out.println();

		String finalTarget = targetPhone;
		String finalContact = contact;

		String lastCachedText = "";
		if (TextCacheHandler.containsCache(finalTarget)) {
			TextCache cache = TextCacheHandler.getTextCache(finalTarget);
			for (Text txt : cache.getTexts()) {
				System.out.println((!txt.getSenderName().equals("You") ? finalContact : "You") + ": "
						+ txt.getText().replaceAll("\n", ""));
			}
			System.out.println();
		}

		Value<String> last = new Value<>(lastCachedText);
		Thread pop3Reader = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.currentThread().isInterrupted()) {
					try {
						List<String> msgs = POP3Handler.getClient().getMessages(finalTarget, 1);
						if (msgs.size() > 0) {
							String msg = msgs.get(msgs.size() - 1);
							if (!msg.equals(last.getValue())) {
								System.out.println("\r" + finalContact + ": " + msg.replaceAll("\n", ""));
								System.out.print("You: ");
								last.setValue(msg);
								TextCacheHandler.cacheText(msg, finalTarget, finalTarget);
							}
						}
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// good
					} catch (Exception e) {
						e.printStackTrace();
						return;
					}
				}
			}
		});
		pop3Reader.start();

		Value<Boolean> value = new Value<>(true);
		while (value.getValue()) {
			String input = Console.writeAndRead("You: ");
			if (input != null) {
				if (input.equals("/exit")) {
					value.setValue(false);
				} else {
					TextCacheHandler.cacheText(input, finalTarget, "You");
					new Thread(new Runnable() {
						public void run() {
							if (!SMTPHandler.sendSMSMessageViaSMTP(input, finalTarget)) {
								value.setValue(false);
							}
						}
					}).start();
				}
			} else {
				return;
			}
		}
		pop3Reader.interrupt();
		Console.EXECUTING_COMMANDS = true;
		Main.startExecution();
	}

	@Override
	public ExecutableCommand getParent() {
		return null;
	}

	@Override
	public CommandHelp getHelp() {
		CommandHelp help = new CommandHelp("sms-open <device> {device=#:<carrier>}", "sms-open",
				"Used for opening new SMS connections.");
		help.addArgumentData("device",
				"The target device to perform the action on, either a phone number or a contact.");
		help.addArgumentData("carrier",
				"Specifies a carrier when opening connections, i.e. \"sprint\". Only required when using a phone number.");
		return help;
	}
}