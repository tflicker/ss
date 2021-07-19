package com.ss.contacts.interfaces.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.MediaType;

import com.ss.contacts.core.db.Contact;
import com.ss.contacts.core.db.ContactCallList;
import com.ss.contacts.core.db.Contacts;
import com.ss.contacts.core.handlers.ContactHandler;

// TODO: (TF) Move all logic to core package
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "contacts")
@Path("/contacts")
public class ContactServlet {
	private static final Logger logger = Logger.getLogger(ContactServlet.class.getName());
	
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Contacts getAllContacts() {
		return (new ContactHandler()).getAllContacts();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response createContact(Contact contact) throws URISyntaxException {
		try {
			ContactHandler handler = new ContactHandler();
			Contact createdContact = handler.createContact(contact);

			return Response
					.status(201)
					.entity(createdContact)
					.contentLocation(new URI(createdContact.getUri()))
					.build();
		} catch (IllegalArgumentException e) {
			return Response.status(400).entity(e.getMessage()).build();
		}
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response updateContact(@PathParam("id") int id, Contact contact) throws URISyntaxException {
		ContactHandler handler = new ContactHandler();
		Contact createdContact = handler.updateContact(id, contact);

		try {
			return Response.status(200)
					.entity(contact)
					.contentLocation(new URI(createdContact.getUri()))
					.build();
		} catch (IllegalArgumentException e) {
			return Response.status(400).entity(e.getMessage()).build();
		} catch (NotFoundException e) {
			return Response.status(404).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response getContactById(@PathParam("id") int id) throws URISyntaxException {
		logger.log(Level.INFO, "Getting contact by ID: "+id);
		
		ContactHandler handler = new ContactHandler();
		Contact contact = handler.getContactById(id);

		try {
			return Response.status(200)
					.entity(contact)
					.contentLocation(new URI(contact.getUri()))
					.build();
		} catch (IllegalArgumentException e) {
			return Response.status(400).entity(e.getMessage()).build();
		} catch (NotFoundException e) {
			return Response.status(404).entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response deleteContact(@PathParam("id") int id) throws URISyntaxException {
		logger.log(Level.INFO, "Deleting contact by ID: "+id);
		
		ContactHandler handler = new ContactHandler();
		Contact contact = handler.deleteContact(id);

		try {
			return Response.status(200)
					.entity(contact)
					.contentLocation(new URI(contact.getUri()))
					.build();
		} catch (IllegalArgumentException e) {
			return Response.status(400).entity(e.getMessage()).build();
		} catch (NotFoundException e) {
			return Response.status(404).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/call-list")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public ContactCallList getContactCallList() {
		logger.log(Level.INFO, "Getting contact call list: ");
		
		ContactCallList callList = (new ContactHandler()).getContactCallList();
		logger.log(Level.INFO, "contact call list size: " + callList.getContacts().size());
		
		return (new ContactHandler()).getContactCallList();
	}
}
