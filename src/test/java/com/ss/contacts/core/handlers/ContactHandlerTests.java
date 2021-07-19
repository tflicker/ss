package com.ss.contacts.core.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.NotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ss.contacts.core.db.Address;
import com.ss.contacts.core.db.CallContact;
import com.ss.contacts.core.db.Contact;
import com.ss.contacts.core.db.ContactCallList;
import com.ss.contacts.core.db.Contacts;
import com.ss.contacts.core.db.DataAccessor;
import com.ss.contacts.core.db.Name;
import com.ss.contacts.core.db.Phone;

class ContactHandlerTests {
	private static Logger logger = Logger.getLogger(ContactHandlerTests.class.getName());
	
	private ContactHandler handler;
	private Contact defaultContact1;
	private Contact defaultContact2;
	private Contact defaultContact3;
	private Contact defaultContact4;
	private Contact defaultContact5;
	
	@BeforeEach
	public void beforeEachTest() {
		logger.log(Level.FINEST, "Setting up before next test");
		
		// Initialize the contact handler for use by each test
		handler = new ContactHandler();
		
		// Make sure we have an empty database
		assertTrue(DataAccessor.dbIsEmpty());
		// Populate the database with default contacts
		defaultContact1 = new Contact();
		defaultContact1.setName(new Name("Tim", "C", "Flicker"));
		defaultContact1.setAddress(new Address("300 My Street", "MyCity", "MyState", "MyZip"));
		defaultContact1.addPhone(new Phone("999-999-9999", "home"));

		defaultContact2 = new Contact();
		defaultContact2.setName(new Name("Harold", "Francis", "Gilkey"));
		defaultContact2.setAddress(new Address("8360 High Autumn Row", "Cannon", "Delaware", "19797"));
		defaultContact2.addPhone(new Phone("302-611-9148", "home"));
		defaultContact2.addPhone(new Phone("302-532-9427" ,"mobile"));
		
		defaultContact3= new Contact();
		defaultContact3.setName(new Name("Anne", "Francis", "Gilkey"));
		defaultContact3.setAddress(new Address("8360 High Autumn Row", "Cannon", "Delaware", "19797"));
		defaultContact3.addPhone(new Phone("302-611-9149", "home"));
		defaultContact3.addPhone(new Phone("302-532-9427" ,"mobile"));
		
		// no home phone so shouldn't be in call list
		defaultContact4 = new Contact();
		defaultContact4.setName(new Name("Jenny", "G", "Flicker"));
		defaultContact4.setAddress(new Address("300 My Street", "MyCity", "MyState", "MyZip"));
		defaultContact4.addPhone(new Phone("999-999-9999", "mobile"));

		defaultContact5 = new Contact();
		defaultContact5.setName(new Name("No", "F", "Number"));
		defaultContact5.setAddress(new Address("300 None Street", "NoCity", "NoState", "NoZip"));
		
		defaultContact1 = handler.createContact(defaultContact1);
		defaultContact2 = handler.createContact(defaultContact2);
		defaultContact3 = handler.createContact(defaultContact3);
		defaultContact4 = handler.createContact(defaultContact4);
		defaultContact5 = handler.createContact(defaultContact5);
	}
	
	@AfterEach
	public void afterEachTest() {
		logger.log(Level.FINEST, "Tearing down after last test");
		
		handler = null;
		
		defaultContact1 = null;
		defaultContact2 = null;
		defaultContact3 = null;
		defaultContact4 = null;
		defaultContact5 = null;
		
		DataAccessor.wipeDB();
	}

	/**
	 * Tests getting all contacts from storage layer
	 */
	@Test
	public void testGetAllContacts() {
		logger.log(Level.INFO, "Testing retrieval of all contacts from the database");
		Contacts contacts = handler.getAllContacts();
		assertEquals(5, contacts.getContactCount(), "We should have 5 contacts in the database");

		assertTrue(contactsAreEqual(contacts.getContact(0), defaultContact1));
		assertTrue(contactsAreEqual(contacts.getContact(1), defaultContact2));
		assertTrue(contactsAreEqual(contacts.getContact(2), defaultContact3));
		assertTrue(contactsAreEqual(contacts.getContact(3), defaultContact4));
		assertTrue(contactsAreEqual(contacts.getContact(4), defaultContact5));
	}
	
	/**
	 * Creates a valid contact persisted to the storage layer
	 */
	@Test
	public void testCreateContact() {
		Contact newContact = new Contact();
		newContact.setName(new Name("My", "New", "Contact"));
		newContact.setAddress(new Address("101 new contact street", "NewCity", "NewState", "NewZip"));
		newContact.addPhone(new Phone("111-111-1111", "work"));
		
		newContact = handler.createContact(newContact);
		Contacts contacts = handler.getAllContacts();
		assertEquals(6, contacts.getContactCount(), "We should have 6 contacts in the database");
		assertTrue(contactsAreEqual(contacts.getContact(5), newContact));
	}
	
	/**
	 * Test creating a contact with no contact data
	 * IllegalArgumentException
	 */
	@Test
	public void testCreateContactNullContact() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			handler.createContact(null);
	    });

	    String expectedMessage = "The contact is not valid";
	    String actualMessage = exception.getMessage();

	    assertEquals(expectedMessage, actualMessage);
	}
	
	/** Test creating a contact with no first name
	 * IllegalArgumentException
	 */
	@Test
	public void testCreateContactNoFirstName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			Contact newContact = new Contact();
			newContact.setName(new Name(null, "New", "Contact"));
			newContact.setAddress(new Address("101 new contact street", "NewCity", "NewState", "NewZip"));
			newContact.addPhone(new Phone("111-111-1111", "work"));
			
			newContact = handler.createContact(newContact);
	    });

	    String expectedMessage = "The contact is not valid";
	    String actualMessage = exception.getMessage();

	    assertEquals(expectedMessage, actualMessage);
	}
	
	/** Test creating a contact with no last name
	 * IllegalArgumentException
	 */
	@Test
	public void testCreateContactNoLastName() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			Contact newContact = new Contact();
			newContact.setName(new Name("My", "New", null));
			newContact.setAddress(new Address("101 new contact street", "NewCity", "NewState", "NewZip"));
			newContact.addPhone(new Phone("111-111-1111", "work"));
			
			newContact = handler.createContact(newContact);
	    });

	    String expectedMessage = "The contact is not valid";
	    String actualMessage = exception.getMessage();

	    assertEquals(expectedMessage, actualMessage);
	}
	
	/**
	 * Tests updating a contact that is persisted to storage layer
	 */
	@Test
	public void testGetContactByIdAndUpdateContact() {
		Contact updatedContact = new Contact();
		updatedContact.setName(new Name("My", "Updated", "Contact"));
		updatedContact.setAddress(new Address("101 updated contact street", "UpdatedCity", "UpdatedState", "UpdatedZip"));
		updatedContact.addPhone(new Phone("111-111-1111", "work"));
		
		// Verify existing contact information
		Contact existingContact = handler.getContactById(1); // This should be defaultcontact2
		int existingContactID = existingContact.getId();
		
		logger.log(Level.INFO, "existingContact name: " + existingContact.getName());
		logger.log(Level.INFO, "default contact name: " + defaultContact2.getName());
		
		assertTrue(contactsAreEqual(existingContact, defaultContact2));
		
		logger.log(Level.INFO, "contact id: " + existingContactID);
		
		// Update the contact
		handler.updateContact(existingContactID, updatedContact);
		
		// Verify the new information is in the database
		Contact verifyExistingContact = handler.getContactById(1); // This should have updatedContact info
		assertTrue(contactsAreEqual(verifyExistingContact, updatedContact));
	}
	
	/**
	 * Tests updating a contact with a bad ID
	 * IllegalArgumentException
	 */
	@Test
	public void testUpdateContactBadID() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			Contact updatedContact = new Contact();
			updatedContact.setName(new Name("My", "Updated", "Contact"));
			updatedContact.setAddress(new Address("101 updated contact street", "UpdatedCity", "UpdatedState", "UpdatedZip"));
			updatedContact.addPhone(new Phone("111-111-1111", "work"));
			
			// Verify existing contact information
			Contact existingContact = handler.getContactById(1); // This should be defaultcontact2
			int existingContactID = -1;//existingContact.getId();

			// Update the contact
			handler.updateContact(existingContactID, updatedContact);
	    });

	    String expectedMessage = "The supplied ID must be equal to or greater than 0";
	    String actualMessage = exception.getMessage();

	    assertEquals(expectedMessage, actualMessage);
	}
	
	/**
	 * Tests updating a contact that doesn't exist in the database
	 * NotFoundException
	 */
	@Test
	public void testUpdateContactNonExisting() {
		Exception exception = assertThrows(NotFoundException.class, () -> {
			Contact updatedContact = new Contact();
			updatedContact.setName(new Name("My", "Updated", "Contact"));
			updatedContact.setAddress(new Address("101 updated contact street", "UpdatedCity", "UpdatedState", "UpdatedZip"));
			updatedContact.addPhone(new Phone("111-111-1111", "work"));
			
			int nonExistingContactID = handler.getAllContacts().getContactCount() + 1;

			// Update the contact
			handler.updateContact(nonExistingContactID, updatedContact);
	    });

	    String expectedMessage = "The contact you are trying to upldate was not found";
	    String actualMessage = exception.getMessage();

	    assertEquals(expectedMessage, actualMessage);
	}
	
	/**
	 * Tests getting a contact with a bad ID
	 * IllegalArgumentException
	 */
	@Test
	public void testGetContactBadId() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			handler.getContactById(-1);
	    });

	    String expectedMessage = "The supplied ID must be equal to or greater than 0";
	    String actualMessage = exception.getMessage();

	    assertEquals(expectedMessage, actualMessage);
	}
	
	/**
	 * Tests getting a contact that doesn't exist
	 * NotFoundException
	 */
	@Test
	public void testGetContactNonExisting() {
		Exception exception = assertThrows(NotFoundException.class, () -> {
			int nonExistingContactID = handler.getAllContacts().getContactCount() + 1;

			handler.getContactById(nonExistingContactID);
	    });

	    String expectedMessage = "The contact you are trying to get was not found";
	    String actualMessage = exception.getMessage();

	    assertEquals(expectedMessage, actualMessage);
	}
	
	/**
	 * Tests deleting a contact from storage layer
	 */
	@Test
	public void testDeleteContact() {
		// Verify existing contact information
		Contact existingContact = handler.getContactById(1); // This should be defaultcontact2
		int existingContactID = existingContact.getId();
		
		assertTrue(contactsAreEqual(existingContact, defaultContact2));
		
		logger.log(Level.INFO, "contact id: " + existingContactID);
		
		// Delete the contact
		handler.deleteContact(existingContactID);
		
		Exception exception = assertThrows(NotFoundException.class, () -> {
			handler.getContactById(1);
	    });

	    String expectedMessage = "The contact you are trying to get was not found";
	    String actualMessage = exception.getMessage();

	    assertEquals(expectedMessage, actualMessage);
	}
	
	/**
	 * Tests deleting a contactg with a bad ID
	 * IllegalArgumentException
	 */
	@Test
	public void testDeleteContactBadId() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			handler.deleteContact(-1);
	    });

	    String expectedMessage = "The supplied ID must be equal to or greater than 0";
	    String actualMessage = exception.getMessage();

	    assertEquals(expectedMessage, actualMessage);
	}
	
	/**
	 * Tests deleting a contact that doesn't exist
	 * NotFoundException
	 */
	@Test
	public void testDeleteContactNonExisting() {
		Exception exception = assertThrows(NotFoundException.class, () -> {
			int nonExistingContactID = handler.getAllContacts().getContactCount() + 1;

			handler.deleteContact(nonExistingContactID);
	    });

	    String expectedMessage = "The contact you are trying to delete was not found";
	    String actualMessage = exception.getMessage();

	    assertEquals(expectedMessage, actualMessage);
	}
	
	/**
	 * Tests getting the contact call list.
	 */
	@Test
	public void testGetContactCallList() {
		ContactCallList callList = handler.getContactCallList();
		
		// Only contacts that contain a home phone should be in the list sorted by last name then first.
		assertEquals(3, callList.getContactCount(), "We should have 3 contacts in the call list");
		
		assertTrue(contactsAreEqual(callList.getContact(0), defaultContact1));
		assertTrue(contactsAreEqual(callList.getContact(1), defaultContact3));
		assertTrue(contactsAreEqual(callList.getContact(2), defaultContact2));
	}
	
	// TODO: (TF) Currently, this method only checks that first and last names match. Expand this to include all data
	// TODO: (TF) Consolidate the below methods as the logic is the same. 
	private Boolean contactsAreEqual(Contact c1, Contact c2) {
		Boolean retVal = Boolean.FALSE;
		
		if(c1 == null && c2 == null) {
			retVal = Boolean.TRUE;
		} else if(c1 != null && c2 != null) {
			Name n1 = c1.getName();
			Name n2 = c2.getName();
			
			if((n1 == null && n2 == null) || (n1 != null && n2 != null && n1.getFirst().equals(n2.getFirst()) && n1.getLast().equals(n2.getLast()))) {
				logger.log(Level.FINE, "first and last names match");
				retVal = Boolean.TRUE;
			}
		}
		
		return retVal;
	}
	
	private Boolean contactsAreEqual(CallContact c1, Contact c2) {
		Boolean retVal = Boolean.FALSE;
		
		if(c1 == null && c2 == null) {
			retVal = Boolean.TRUE;
		} else if(c1 != null && c2 != null) {
			Name n1 = c1.getName();
			Name n2 = c2.getName();

			logger.log(Level.INFO, "n1: " + n1);
			logger.log(Level.INFO, "n2: " + n2);
			
			if((n1 == null && n2 == null) || (n1 != null && n2 != null && n1.getFirst().equals(n2.getFirst()) && n1.getLast().equals(n2.getLast()))) {
				logger.log(Level.INFO, "first and last names match");
				retVal = Boolean.TRUE;
			}
		}
		
		return retVal;
	}
}
