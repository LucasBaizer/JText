package com.sms;

import java.util.ArrayList;
import java.util.Arrays;

public class ContactHandler {
	private static ArrayList<Contact> contacts = new ArrayList<>();

	public static void initializeContacts() {
		if (!SaveData.isNewData()) {
			if (SaveData.getSaveData().getContacts() != null) {
				contacts = new ArrayList<>(Arrays.asList(SaveData.getSaveData().getContacts()));
			}
		}
	}

	public static void addContact(Contact person) {
		contacts.add(person);
		SaveData.getSaveData().addContact(person);
	}

	public static boolean removeContact(Contact person) {
		boolean is = contacts.remove(person);
		if (is) {
			SaveData.getSaveData().removeContact(person);
		}
		return is;
	}

	public static Contact getContact(String name) {
		for (Contact c : contacts) {
			if (c.getContactName().equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
	}

	public static Contact getContactByNumber(String num) {
		for (Contact c : contacts) {
			if (c.getPhoneNumber().equalsIgnoreCase(num)) {
				return c;
			}
		}
		return null;
	}

	public static Contact[] getContacts() {
		if (contacts.size() > 0)
			return contacts.toArray(new Contact[contacts.size()]);
		return null;
	}
}