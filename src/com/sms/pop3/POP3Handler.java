package com.sms.pop3;

import com.sms.command.ConsoleLoadingBar;

public class POP3Handler {
	private static String pop3Server = "pop.gmail.com";
	private static int pop3Port = 995;

	private static POP3Client client;

	public static void createClient(String username, String password) {
		createClient(username, password, null);
	}

	public static void createClient(String username, String password, ConsoleLoadingBar loader) {
		if (client != null) {
			client.disconnect();
		}
		client = new POP3Client();

		client.setUsername(username);
		client.setPassword(password);
		client.setHost(pop3Server);
		client.setPort(pop3Port);
		client.connect();
	}

	public static boolean isLoggedIn() {
		return client != null && client.isConnected();
	}

	public static POP3Client getClient() {
		return client;
	}

	public static void setClient(POP3Client client) {
		POP3Handler.client = client;
	}

	public static String getPOP3Server() {
		return pop3Server;
	}

	public static void setPOP3Server(String pop3Server) {
		POP3Handler.pop3Server = pop3Server;
	}

	public static int getPOP3Port() {
		return pop3Port;
	}

	public static void setPOP3Port(int pop3Port) {
		POP3Handler.pop3Port = pop3Port;
	}
}