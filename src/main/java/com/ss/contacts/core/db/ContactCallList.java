package com.ss.contacts.core.db;

import java.util.ArrayList;
import java.util.Collections;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "contacts")
public class ContactCallList {
	@XmlElement(name = "contact")
	private ArrayList<CallContact> contacts;

	public ArrayList<CallContact> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<CallContact> contacts) {
		this.contacts = contacts;
	}

	public void addContact(CallContact contact) {
		if (contacts == null) {
			contacts = new ArrayList<>();
		}

		contacts.add(contact);
	}

	public void sort() {
		Collections.sort(contacts);
	}

	/////////////////////////
	// Convenience methods //
	/////////////////////////
	public CallContact getContact(int index) {
		// TODO: (TF) Handle index out of bounds and nulls?
		return contacts.get(index);
	}

	public int getContactCount() {
		int retVal = 0;

		if (contacts != null) {
			retVal = contacts.size();
		}

		return retVal;
	}
}
