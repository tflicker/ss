package com.ss.contacts.core.db;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "name")
public class Name extends AbstractObject implements Comparable<Name> {
	@XmlElement(name = "first")
	private String first;

	@XmlElement(name = "middle")
	private String middle;

	@XmlElement(name = "last")
	private String last;

	public Name() {
		super();
	}

	public Name(String first, String middle, String last) {
		this();
		this.setFirst(first);
		this.setMiddle(middle);
		this.setLast(last);
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	// TODO: (TF) Should we worry about case here?
	@Override
	public int compareTo(Name otherName) {
		if (otherName == null) {
			return 1;
		} 

		int last = getLast().compareTo(otherName.getLast());

		if (last == 0) {
			return getFirst().compareTo(otherName.getFirst());
		} else {
			return last;
		}
	}
	
	// TODO: (TF) Check for null middle name
	@Override
	public String toString() {
		return this.getFirst() + " " + this.getMiddle() + " " + this.getLast();
	}
}
