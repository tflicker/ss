package com.ss.contacts.core.handlers;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.NotFoundException;

import com.ss.contacts.core.db.Contact;
import com.ss.contacts.core.db.ContactCallList;
import com.ss.contacts.core.db.Contacts;
import com.ss.contacts.core.db.DataAccessor;

public class ContactHandler {
	private static Logger logger = Logger.getLogger(ContactHandler.class.getName());

	public ContactHandler() {
		super();
	}

	/**
	 * @return Return all contacts in the system
	 */
	public Contacts getAllContacts() {
		return DataAccessor.getAllContacts();
	}

	public Contact createContact(Contact contact) {
		// Validate contact data before persisting to the database
		validateContact(contact);

		return DataAccessor.createContact(contact);
	}

	public Contact updateContact(int id, Contact contact) {
		if (id < 0) {
			logger.log(Level.INFO, "The ID of the contact to update is not valid: " + id);
			throw new IllegalArgumentException("The supplied ID must be equal to or greater than 0");
		}

		// Validate contact data before persisting to the database
		validateContact(contact);
		
		Contact updatedContact = DataAccessor.updateContact(id, contact);

		logger.log(Level.INFO, "updatedContact: " + updatedContact);
		
		if(updatedContact == null) {
			logger.log(Level.INFO, "Could not find the contact in the database");
			
			// TODO: (TF) Convert this to a custom error if you have time
			throw new NotFoundException("The contact you are trying to upldate was not found");
		}
		
		return updatedContact;
	}
	
	public Contact getContactById(int id) {
		if (id < 0) {
			logger.log(Level.INFO, "The ID of the contact to update is not valid: " + id);
			throw new IllegalArgumentException("The supplied ID must be equal to or greater than 0");
		}

		Contact contact = DataAccessor.getContactById(id);
		
		if(contact == null) {
			logger.log(Level.INFO, "Could not find the contact in the database");
			
			// TODO: (TF) Convert this to a custom error if you have time
			throw new NotFoundException("The contact you are trying to get was not found");
		}
		
		return contact;
	}
	
	public Contact deleteContact(int id) {
		if (id < 0) {
			logger.log(Level.INFO, "The ID of the contact to delete is not valid: " + id);
			throw new IllegalArgumentException("The supplied ID must be equal to or greater than 0");
		}

		Contact contact = DataAccessor.deleteContact(id);
		
		if(contact == null) {
			logger.log(Level.INFO, "Could not find the contact in the database");
			
			// TODO: (TF) Do we actually care if there isn't an existing entry at that ID?
			throw new NotFoundException("The contact you are trying to delete was not found");
		}
		
		return contact;
	}
	
	/**
	 * @return Return all contacts in the system
	 */
	public ContactCallList getContactCallList() {
		return DataAccessor.getContactCallList();
	}

	  /////////////////////////////
	 // Data validation methods //
	/////////////////////////////
	private void validateId(int id) {
		
	}
	
	private void validateContact(Contact contact) {
		logger.log(Level.FINE, "Validating contact: " + contact);

		// TODO: (TF) Validate phone type and email
		if (contact == null || contact.getName() == null || contact.getName().getFirst() == null || contact.getName().getLast() == null) {
			logger.log(Level.FINE, "The contact is not valid");
			throw new IllegalArgumentException("The contact is not valid");
		}
	}
}