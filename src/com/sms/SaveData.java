package com.sms;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class SaveData implements Serializable {
	public static class SaveDataCacheHandler implements CacheHandler<SaveData> {
		private static SaveDataCacheHandler theHandler = new SaveDataCacheHandler();

		public void initialize() {
			try {
				if (SAVE_FILE.exists()) {
					theData = deserializeSave(SAVE_FILE);
				} else {
					SAVE_FILE.createNewFile();
					theData = new SaveData();
					theData.save();
					isNewData = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public static SaveDataCacheHandler getSaveDataCacheHandler() {
			return theHandler;
		}
	}

	private static final long serialVersionUID = -3878475546040649119L;

	private static File SAVE_FILE = new File(System.getProperty("user.dir") + "\\save.cache");

	private String smtpEmail;
	private String smtpPassword;
	private String pop3Email;
	private String pop3Password;
	private Contact[] contacts;
	private Carrier[] customCarriers;

	private String smtpServer = "smtp.gmail.com:587";
	private String pop3Server = "pop.gmail.com:995";

	private static SaveData theData;
	private static boolean isNewData = false;

	public static SaveData getSaveData() {
		return theData;
	}

	public static boolean isNewData() {
		return isNewData;
	}

	public Contact[] getContacts() {
		return contacts;
	}

	public void addContact(Contact c) {
		ArrayList<Contact> list = new ArrayList<>();
		if (contacts != null) {
			list = new ArrayList<>(Arrays.asList(contacts));
		}
		list.add(c);
		contacts = list.toArray(new Contact[list.size()]);
	}

	public void removeContact(Contact c) {
		ArrayList<Contact> list = new ArrayList<>();
		if (contacts != null) {
			list = new ArrayList<>(Arrays.asList(contacts));
		}
		list.remove(c);
		contacts = list.toArray(new Contact[list.size()]);
	}

	public void addCustomCarrier(Carrier c) {
		ArrayList<Carrier> list = new ArrayList<>();
		if (customCarriers != null) {
			list = new ArrayList<>(Arrays.asList(customCarriers));
		}
		list.add(c);
		customCarriers = list.toArray(new Carrier[list.size()]);
	}

	public void removeCustomCarrier(Carrier c) {
		ArrayList<Carrier> list = new ArrayList<>();
		if (customCarriers != null) {
			list = new ArrayList<>(Arrays.asList(customCarriers));
		}
		list.remove(c);
		customCarriers = list.toArray(new Carrier[list.size()]);
	}

	public void save() throws IOException {
		SaveDataCacheHandler.getSaveDataCacheHandler().serializeSave(SAVE_FILE, this);
	}

	public Carrier[] getCustomCarriers() {
		return customCarriers;
	}

	public void setCustomCarriers(Carrier[] customCarriers) {
		this.customCarriers = customCarriers;
	}

	public String getSmtpEmail() {
		return smtpEmail;
	}

	public void setSmtpEmail(String smtpEmail) {
		this.smtpEmail = smtpEmail;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public String getPop3Email() {
		return pop3Email;
	}

	public void setPop3Email(String pop3Email) {
		this.pop3Email = pop3Email;
	}

	public String getPop3Password() {
		return pop3Password;
	}

	public void setPop3Password(String pop3Password) {
		this.pop3Password = pop3Password;
	}

	public String getPreferredSmtpServer() {
		return smtpServer;
	}

	public void setPreferredSmtpServer(String s) {
		this.smtpServer = s;
	}

	public String getPreferredPop3Server() {
		return pop3Server;
	}

	public void setPreferredPop3Server(String pop3Server) {
		this.pop3Server = pop3Server;
	}
}