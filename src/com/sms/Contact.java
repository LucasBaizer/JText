package com.sms;

import java.io.Serializable;

public class Contact implements Serializable {
	private static final long serialVersionUID = 8514171093020412724L;

	private String contactName;
	private String phoneNumber;
	private Carrier carrier;

	public Contact(String contactName, String phoneNumber, Carrier carrier) {
		this.contactName = contactName;
		this.phoneNumber = phoneNumber;
		this.carrier = carrier;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}
}
