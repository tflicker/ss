package com.ss.contacts.interfaces.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class ServletLoader extends ResourceConfig {

	public ServletLoader() {
		register(ContactServlet.class);
	}
}