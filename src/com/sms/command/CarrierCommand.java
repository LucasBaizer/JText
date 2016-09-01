package com.sms.command;

import com.sms.Carrier;
import com.sms.CarrierHelper;
import com.sms.command.help.ArgumentHelp;
import com.sms.command.help.CommandHelp;

public class CarrierCommand extends ExecutableCommand {
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
			if (CarrierHelper.addCustomCarrier(new Carrier(args.getArguments()[2], args.getArguments()[3].toLowerCase(),
					args.getArguments()[4].toLowerCase()))) {
				System.out.println("Added carrier.");
			} else {
				System.out.println("A carrier with the alias \"" + args.getArguments()[3] + "\" already exists!");
			}
		} else if (args.getArguments()[1].equals("remove")) {
			if (CarrierHelper.removeCustomCarrier(args.getArguments()[2])) {
				System.out.println("Removed carrier.");
			} else {
				System.out.println("No carrier found with alias \"" + args.getArguments()[2] + "\".");
			}
		} else if (args.getArguments()[1].equals("list")) {
			System.out.println("Predefined carriers:");
			int name = getLongest(CarrierHelper.getCarriers(), 0);
			int alias = getLongest(CarrierHelper.getCarriers(), 1);
			System.out.println("\tName" + repeatSpace(name) + "Alias" + repeatSpace(alias - 1) + "Address");
			System.out.println();
			for (Carrier c : CarrierHelper.getCarriers()) {
				System.out.println("\t" + c.getName() + repeatSpace(name - c.getName().length() + 4) + c.getAlias()
						+ repeatSpace(alias - c.getAlias().length() + 4) + c.getAddress());
			}
			System.out.println();
			System.out.println("User-defined carriers:");
			if (CarrierHelper.getCustomCarriers() != null && CarrierHelper.getCustomCarriers().length > 0) {
				int cname = getLongest(CarrierHelper.getCustomCarriers(), 0);
				int calias = getLongest(CarrierHelper.getCustomCarriers(), 1);
				System.out.println("\tName" + repeatSpace(cname) + "Alias" + repeatSpace(calias) + "Address");
				System.out.println();
				for (Carrier c : CarrierHelper.getCustomCarriers()) {
					System.out.println("\t" + c.getName() + repeatSpace(cname - c.getName().length() + 4) + c.getAlias()
							+ repeatSpace(calias - c.getAlias().length() + 5) + c.getAddress());
				}
			} else {
				System.out.println("\tNo user-defined carriers.");
			}
		}
	}

	private String repeatSpace(int times) {
		return new String(new char[times]).replace("\0", " ");
	}

	private int getLongest(Carrier[] carriers, int col) {
		int longest = 0;
		if (col == 0) {
			for (Carrier c : carriers) {
				int length = c.getName().length();
				if (length > longest) {
					longest = length;
				}
			}
		} else if (col == 1) {
			for (Carrier c : carriers) {
				int length = c.getAlias().length();
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
				"carrier <add|remove|list> {add:<name>} {add|remove:<alias>} {add:<address>}", "carrier",
				"Base command for carrier information.");
		ArgumentHelp add = new ArgumentHelp("add", "Specifies that the command will add a user-defined carrier.");
		ArgumentHelp remove = new ArgumentHelp("remove",
				"Specifies that the command will remove a user-defined carrier.");
		ArgumentHelp list = new ArgumentHelp("list", "Specifies that the command will list availiable carriers.");
		help.addArgumentData(new ArgumentHelp("add|remove|list", "Specifies the command's action.", add, remove, list));
		help.addArgumentData(new ArgumentHelp("name",
				"Specifies a name brand for a carrier when adding a user-defined carrier (i.e. \"T-Mobile\")."));
		help.addArgumentData(
				new ArgumentHelp("alias", "Specifies the one-word unique reference for a carrier (i.e. \"tmobile\")."));
		help.addArgumentData(
				new ArgumentHelp("address", "Specifies the address extension for a carrier (i.e. \"tmomail.net\")."));
		return help;
	}

	@Override
	public ExecutableCommand getParent() {
		return null;
	}
}