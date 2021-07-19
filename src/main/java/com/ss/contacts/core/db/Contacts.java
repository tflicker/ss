package com.ss.contacts.core.db;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "contacts")
public class Contacts {
	@XmlElement(name = "contact")
	private ArrayList<Contact> contacts;

	public ArrayList<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<Contact> contacts) {
		this.contacts = contacts;
	}
	
	  /////////////////////////
	 // Convenience methods //
	/////////////////////////
	public Contact getContact(int index) {
		// TODO: (TF) Handle index out of bounds and nulls?
		return contacts.get(index);
	}
	
	public int getContactCount() {
		int retVal = 0;
		
		if(contacts != null) {
			retVal = contacts.size();
		}
		
		return retVal;
	}
}
