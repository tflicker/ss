package com.ss.contacts.core.db;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "phone")
public class Phone extends AbstractObject {
	//private enum PhoneType {home, work, mobile};
	
	@XmlElement(name = "number")
	private String number;
	
	// TODO: (TF) Refactor possibly using an enum for type
	@XmlElement(name = "type")
	private String type;
	
	public Phone() {
		super();
	}
	
	public Phone(String number, String type) {
		this();
		this.setNumber(number);
		this.setType(type);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
