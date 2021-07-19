package com.ss.contacts.core.db;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class Address extends AbstractObject {
	@XmlElement(name = "street")
	private String street;

	@XmlElement(name = "city")
	private String city;

	@XmlElement(name = "state")
	private String state;

	@XmlElement(name = "zip")
	private String zip;

	public Address() {
		super();
	}
	
	public Address(String street, String city, String state, String zip) {
		this();
		this.setStreet(street);
		this.setCity(city);
		this.setState(state);
		this.setZip(zip);
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String streetSs) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
