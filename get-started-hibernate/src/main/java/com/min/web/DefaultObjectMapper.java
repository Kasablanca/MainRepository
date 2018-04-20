package com.min.web;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;

public class DefaultObjectMapper extends ObjectMapper {
	
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		Hibernate5Module module = new Hibernate5Module();
		module.configure(Feature.FORCE_LAZY_LOADING, true);
		registerModule(module);
	}
	
}
