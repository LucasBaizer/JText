package com.sms;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SMTPHandler {
	private static Session smtpSession;

	public static Session getSMTPSession() {
		return smtpSession;
	}

	public static void setSMTPSession(Session smtpSession) {
		if (smtpSession != null) {
			smtpSession.getProperties().put("mail.smtp.host", smtpServer);
			smtpSession.getProperties().put("mail.smtp.port", smtpPort);
		}

		SMTPHandler.smtpSession = smtpSession;
	}

	public static boolean isLoggedIn() {
		return smtpSession != null;
	}

	public static boolean sendSMSMessageViaSMTP(String msg, String target) {
		try {
			Message message = new MimeMessage(smtpSession);
			message.setFrom(new InternetAddress(getCurrentEmailAddress()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(target));
			message.setText(msg);
			Transport.send(message);
			return true;
		} catch (NullPointerException e) {
			return false;
		} catch (Exception e) {
			System.out.println("Error sending message: " + e.getClass() + ": " + e.getMessage());
			return false;
		}
	}

	private static String currentEmailAddress;
	private static String currentPassword;

	private static String smtpServer = "smtp.gmail.com";
	private static int smtpPort = 587;

	public static String getCurrentEmailAddress() {
		return currentEmailAddress;
	}

	public static void setCurrentEmailAddress(String c) {
		currentEmailAddress = c;
	}

	public static String getCurrentPassword() {
		return currentPassword;
	}

	public static void setCurrentPassword(String c) {
		currentPassword = c;
	}

	public static String getSMTPServer() {
		return smtpServer;
	}

	public static void setSMTPServer(String smtpServer) {
		SMTPHandler.smtpServer = smtpServer;
	}

	public static int getSMTPPort() {
		return smtpPort;
	}

	public static void setSMTPPort(int smtpPort) {
		SMTPHandler.smtpPort = smtpPort;
	}
}