package com.sit.abbra.abbraapi.web.config;

import org.glassfish.jersey.server.ResourceConfig;

import com.sit.abbra.abbraapi.web.filter.RequestServerFilter;
import com.sit.abbra.abbraapi.web.filter.ResponseServerFilter;

public class ApplicationConfig extends ResourceConfig {

	public ApplicationConfig() {
		packages("com.sit.authen.ws", "com.sit.pibics.eextensionapi");
		
		register(RequestServerFilter.class);
		register(ResponseServerFilter.class);
	}

}
