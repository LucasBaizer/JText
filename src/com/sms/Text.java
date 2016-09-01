package com.sms;

import java.io.Serializable;

public class Text implements Serializable {
	private static final long serialVersionUID = 8913942933138985216L;

	private String senderName;
	private String text;

	public Text(String sender, String txt) {
		this.senderName = sender;
		this.text = txt;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
