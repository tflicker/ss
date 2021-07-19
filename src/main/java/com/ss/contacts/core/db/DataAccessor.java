package com.ss.contacts.core.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataAccessor {
	private static Logger logger = Logger.getLogger(DataAccessor.class.getName());
	
	// TODO: (TF) Manage this contact collection in a database rather than in-memory
	private static Map<Integer, Contact> DB = new HashMap<>();

	/*
	static {
		Contact c1 = new Contact();
		c1.setName(new Name("Tim", "C", "Flicker"));
		c1.setAddress(new Address("305 Glen Bonnie Lane", "Cary", "NC", "27511"));
		c1.addPhone(new Phone("999-999-9999", "home"));

		Contact c2 = new Contact();
		c2.setName(new Name("Harold", "Francis", "Gilkey"));
		c2.setAddress(new Address("8360 High Autumn Row", "Cannon", "Delaware", "19797"));
		c2.addPhone(new Phone("302-611-9148", "home"));
		c2.addPhone(new Phone("302-532-9427" ,"mobile"));

		DB.put(1, c1);
		DB.put(0, c2);
	}
	*/

	/**
	 * @return Returns all contacts in the system
	 */
	public static Contacts getAllContacts() {
		logger.log(Level.INFO, "Getting all contacts in the database");
		
		Contacts contacts = new Contacts();
		contacts.setContacts(new ArrayList<>(DB.values()));
		return contacts;
	}

	/**
	 * Persists a new contact. If the contact arg already contains an ID, a new one
	 * will be assigned
	 * 
	 * @param contact - The contact to persist
	 * @return Returns the persisted contact with assigned ID
	 */
	public static Contact createContact(Contact contact) {
		logger.log(Level.FINE, "Creating contact");
		
		contact.setId(DB.values().size());
		DB.put(contact.getId(), contact);

		return contact;
	}
	
	/**
	 * Updates a specific contact
	 * @param id - The ID of the contact to update
	 * @param contact - The updated contact information
	 * @return Returns the updated contact along with the ID
	 */
	public static Contact updateContact(int id, Contact contact) {
		Contact temp = DB.get(id);
		
		logger.log(Level.INFO, id + ": " + id + ", temp: " + temp);
		
		if(temp != null) {
			contact.setId(id);
			DB.put(id, contact);
			return contact;
		} 
		
		return null;
	}
	
	/** 
	 * @param id - The ID of the contact to get
	 * @return Returns the contact or null if not found
	 */
	public static Contact getContactById(int id) {
		return DB.get(id);
	}
	
	/**
	 * Removes a contact from the database
	 * @param id - The ID of the contact to delete
	 * @return Returns the contact if deleted or null if not found
	 */
	public static Contact deleteContact(int id) {
		Contact contact = DB.get(id);
		
		if(contact != null) {
			contact.setId(id);
			DB.remove(id);
			return contact;
		} 
		
		return null;
	}
	
	/**
	 * @return Returns the calls list. 
	 */
	public static ContactCallList getContactCallList() {
		// TODO: (TF) Refactor this when using a database so the filtering and sorting are done at the db level
		ContactCallList contacts = new ContactCallList();
		
		Contact next = null;
		String nextHomePhone = null;
		for(Integer key : DB.keySet()) {
			next = DB.get(key);
			nextHomePhone = getHomePhone(next);
			
			if(next != null && next.getName() != null && nextHomePhone != null) {
				logger.log(Level.FINE, "adding user to call list: " + next.getName());
				contacts.addContact(new CallContact(next.getName(), nextHomePhone));
			}
		}
		
		// TODO: (TF) Sort the call list. This should probably be done at the database level
		contacts.sort();
		
		return contacts;
	}
	
	  ////////////////////
	 // Helper methods //
	////////////////////
	
	// TODO: (TF) Refactor possibly using an enum for phone type
	private static String getHomePhone(Contact contact) {
		String retVal = null;
		
		if(contact != null && contact.getPhone() != null && contact.getPhone().size() > 0) {
			ArrayList<Phone> phones = contact.getPhone();
			
			for(Phone phone : phones) {
				if(phone.getType().toLowerCase().equals("home")) {
					retVal = phone.getNumber();
					break;
				}
			}
		}
		
		return retVal;
	}
	
	// TODO: (TF) This method should not be accessible. It's currently in place for testing
	@Deprecated
	public static void wipeDB() {
		DB.clear();
	}
	
	public static Boolean dbIsEmpty() {
		return DB.size() == 0;
	}
}
