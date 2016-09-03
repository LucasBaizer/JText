package com.sms.pop3;

import java.util.ArrayList;
import java.util.List;

import com.sms.ContactHandler;
import com.sms.TextCacheHandler;

import jodd.mail.EmailFilter;
import jodd.mail.EmailMessage;
import jodd.mail.Pop3SslServer;
import jodd.mail.ReceiveMailSession;
import jodd.mail.ReceivedEmail;

public class POP3Client {
	private String username;
	private String password;
	private String host;
	private int port;
	private boolean connected = false;
	private Pop3SslServer popServer;
	private ReceiveMailSession session;

	public void connect() {
		if (session != null && connected) {
			disconnect();
		}

		popServer = new Pop3SslServer(host, port, username, password);
		session = popServer.createSession();
		session.open();
		connected = true;
	}

	public List<String> getMessages(String from, int cap) {
		ArrayList<String> msgs = new ArrayList<>();

		connect();

		try {
			session.useDefaultFolder();
		} catch (IllegalStateException e) {
			System.err.println("Error retrieving messages. Please try again later.");
			return null;
		}

		ReceivedEmail[] emails = session.receiveEmailAndDelete(new EmailFilter().from(from));
		if (emails != null) {
			for (ReceivedEmail email : emails) {
				if (cap == msgs.size())
					break;
				List<EmailMessage> messages = email.getAllMessages();
				for (EmailMessage msg : messages) {
					msgs.add(msg.getContent());
				}
			}
		}

		return msgs;
	}

	public List<String> getMessagesFromUnknownSources() {
		ArrayList<String> msgs = new ArrayList<>();

		connect();

		try {
			session.useDefaultFolder();
		} catch (IllegalStateException e) {
			System.err.println("Error retrieving messages. Please try again later.");
			return null;
		}

		ReceivedEmail[] emails = session.receiveEmailAndDelete();
		if (emails != null) {
			for (ReceivedEmail email : emails) {
				if (ContactHandler.getContactByNumber(email.getFrom().getEmail()) == null
						&& TextCacheHandler.getTextCache(email.getFrom().getEmail()) == null) {
					List<EmailMessage> messages = email.getAllMessages();
					for (EmailMessage msg : messages) {
						msgs.add(email.getFrom().getEmail() + ": " + msg.getContent());
					}
				}
			}
		}

		return msgs;
	}

	public void disconnect() {
		if (session != null && connected) {
			try {
				session.close();
			} catch (IllegalStateException e) {
				return;
			}
			connected = false;
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isConnected() {
		return connected;
	}

	public ReceiveMailSession getSession() {
		return session;
	}

	public void setSession(ReceiveMailSession session) {
		this.session = session;
	}
}