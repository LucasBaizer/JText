package com.sms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CarrierHelper {
	private static final ArrayList<Carrier> carriers = new ArrayList<>();
	private static ArrayList<Carrier> customCarriers = new ArrayList<>();

	static {
		addCarrier("AT&T", "att", "txt.att.net");
		addCarrier("Comcast", "comcast", "comcastpcs.textmsg.com");
		addCarrier("Metro PCS", "metro", "mymetropcs.com");
		addCarrier("Sprint", "sprint", "messaging.sprintpcs.com");
		addCarrier("T-Mobile", "tmobile", "tmomail.net");
		addCarrier("Verizon", "verizon", "vtext.com");
		addCarrier("Virgin Mobile", "vmobile", "vmobl.com");
	}

	public static void addCarrier(String name, String alias, String address) {
		carriers.add(new Carrier(name, alias, address));
	}

	public static void initializeCustomCarriers() {
		if (!SaveData.isNewData()) {
			if (SaveData.getSaveData().getCustomCarriers() != null) {
				customCarriers = new ArrayList<>(Arrays.asList(SaveData.getSaveData().getCustomCarriers()));
			}
		}
	}

	public static Carrier[] getCarriers() {
		ArrayList<Carrier> array = new ArrayList<>(carriers);
		Collections.sort(array);
		return array.toArray(new Carrier[array.size()]);
	}

	public static Carrier[] getCustomCarriers() {
		if (customCarriers.size() == 0)
			return null;
		ArrayList<Carrier> array = new ArrayList<>(customCarriers);
		Collections.sort(array);
		return array.toArray(new Carrier[array.size()]);
	}

	public static Carrier getAnyCarrier(String alias) {
		for (Carrier c : carriers) {
			if (c.getAlias().equals(alias)) {
				return c;
			}
		}
		for (Carrier c : customCarriers) {
			if (c.getAlias().equals(alias)) {
				return c;
			}
		}
		return null;
	}

	public static Carrier getCustomCarrier(String alias) {
		for (Carrier c : customCarriers) {
			if (c.getAlias().equals(alias)) {
				return c;
			}
		}
		return null;
	}

	public static boolean addCustomCarrier(Carrier c) {
		for (Carrier c1 : carriers) {
			if (c1.getAlias().equals(c.getAlias())) {
				return false;
			}
		}
		for (Carrier c2 : customCarriers) {
			if (c2.getAlias().equals(c.getAlias())) {
				return false;
			}
		}
		customCarriers.add(c);
		SaveData.getSaveData().addCustomCarrier(c);
		return true;
	}

	public static boolean removeCustomCarrier(String alias) {
		boolean exists = customCarriers.remove(getCustomCarrier(alias));
		if (exists)
			SaveData.getSaveData().removeCustomCarrier(getCustomCarrier(alias));
		return exists;
	}

	public static boolean carrierAdded(String string) {
		return getAnyCarrier(string) != null;
	}
}