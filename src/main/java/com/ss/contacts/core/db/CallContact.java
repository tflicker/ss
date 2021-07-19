package com.ss.contacts.core.db;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * A call contact is someone on the call list
 */
@XmlAccessorType(XmlAccessType.NONE)
public class CallContact extends AbstractObject implements Comparable<CallContact> {
	@XmlElement(name = "name")
	private Name name;
	
	@XmlElement(name = "phone")
	private String phone;
	
	public CallContact() {
		super();
	}
	
	public CallContact(Name name, String phone) {
		this();
		this.setName(name);
		this.setPhone(phone);
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public int compareTo(CallContact otherContact) {
		if (otherContact == null) {
			return 1;
		} 

		return getName().compareTo(otherContact.getName());
	}
}
