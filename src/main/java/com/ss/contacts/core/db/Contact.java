package com.ss.contacts.core.db;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class Contact extends AbstractObject {
	@XmlAttribute(name = "id")
	private int id;

	@XmlElement(name = "name")
	private Name name;

	@XmlElement(name = "address")
	private Address address;
	
	@XmlElement(name = "phone")
	private ArrayList<Phone> phone;
	
	@XmlElement(name = "email")
	private String email;

	public Contact() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public ArrayList<Phone> getPhone() {
		return phone;
	}

	public void setPhone(ArrayList<Phone> phone) {
		this.phone = phone;
	}
	
	/**
	 * Convenience method to add a phone
	 * @param phone
	 */
	public void addPhone(Phone phone) {
		if(this.phone == null) {
			this.phone = new ArrayList<>();
		}
		
		this.phone.add(phone);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// TODO: (TF) This may need to be XML transient. It may also need to change if
	// this instances ID is null
	public String getUri() {
		return "/contacts/" + getId();
	}
}
