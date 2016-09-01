package com.sms.command;

import com.sms.CarrierHelper;
import com.sms.Contact;
import com.sms.ContactHelper;
import com.sms.command.help.ArgumentHelp;
import com.sms.command.help.CommandHelp;

public class ContactCommand extends ExecutableCommand {
	@Override
	public boolean isValid(CommandData args) {
		if (args.getArguments().length >= 2) {
			if (args.getArguments()[1].equals("add")) {
				return args.getArguments().length == 5;
			} else if (args.getArguments()[1].equals("remove")) {
				return args.getArguments().length == 3;
			} else if (args.getArguments()[1].equals("list")) {
				return args.getArguments().length == 2;
			}
		}
		return false;
	}

	@Override
	public void execute(CommandData args) {
		if (args.getArguments()[1].equals("add")) {
			if (CarrierHelper.getAnyCarrier(args.getArguments()[4]) == null) {
				System.out.println("Unknown carrier alias: \"" + args.getArguments()[4] + "\".");
				return;
			}
			if (ContactHelper.getContact(args.getArguments()[2]) != null) {
				System.out.println("A contact with the name \"" + args.getArguments()[2] + "\" already exists.");
				return;
			}

			ContactHelper.addContact(new Contact(args.getArguments()[2], args.getArguments()[3],
					CarrierHelper.getAnyCarrier(args.getArguments()[4])));
			System.out.println("Added contact.");
		} else if (args.getArguments()[1].equals("remove")) {
			if (!ContactHelper.removeContact(ContactHelper.getContact(args.getArguments()[2]))) {
				System.out.println("No contact found with name \"" + args.getArguments()[2] + "\".");
			} else {
				System.out.println("Removed contact.");
			}
		} else if (args.getArguments()[1].equals("list")) {
			System.out.println("Contacts:");
			if (ContactHelper.getContacts() != null && ContactHelper.getContacts().length > 0) {
				int cname = getLongest(ContactHelper.getContacts(), 0);
				int cnumber = getLongest(ContactHelper.getContacts(), 1);
				System.out
						.println("\tName" + repeatSpace(cname) + "Number" + repeatSpace(cnumber - 2) + "Carrier Alias");
				System.out.println();
				for (Contact c : ContactHelper.getContacts()) {
					System.out.println("\t" + c.getContactName() + repeatSpace(cname - c.getContactName().length() + 4)
							+ c.getPhoneNumber() + repeatSpace(cnumber - c.getPhoneNumber().length() + 4)
							+ c.getCarrier().getAlias());
				}
			} else {
				System.out.println("\tYou have no contacts.");
			}
		}
	}

	private String repeatSpace(int times) {
		return new String(new char[times]).replace("\0", " ");
	}

	private int getLongest(Contact[] people, int col) {
		int longest = 0;
		if (col == 0) {
			for (Contact c : people) {
				int length = c.getContactName().length();
				if (length > longest) {
					longest = length;
				}
			}
		} else if (col == 1) {
			for (Contact c : people) {
				int length = c.getPhoneNumber().length();
				if (length > longest) {
					longest = length;
				}
			}
		}
		return longest;
	}

	@Override
	public CommandHelp getHelp() {
		CommandHelp help = new CommandHelp(
				"contact <add|remove|list> {add|remove:<name>} {add:<number>} {add:<carrier>}", "contact",
				"Base command for contacts.");
		ArgumentHelp add = new ArgumentHelp("add", "Specifies that the command will add a new contact.");
		ArgumentHelp remove = new ArgumentHelp("remove", "Specifies that the command will remove an existing contact.");
		ArgumentHelp list = new ArgumentHelp("list", "Specifies that the command will list existing contacts.");
		help.addArgumentData(new ArgumentHelp("add|remove|list", "Specifies the command's action.", add, remove, list));
		help.addArgumentData(
				new ArgumentHelp("name", "Specifies a name for the contact being referenced (i.e. \"Jane Doe\")."));
		help.addArgumentData(new ArgumentHelp("number",
				"Specifies the cell phone number of a new contact being added (i.e. \"0123456789\")."));
		help.addArgumentData(new ArgumentHelp("carrier",
				"Specifies the carrier (alias) of a new contact being added (i.e. \"tmobile\")."));
		return help;
	}

	@Override
	public ExecutableCommand getParent() {
		return null;
	}
}
