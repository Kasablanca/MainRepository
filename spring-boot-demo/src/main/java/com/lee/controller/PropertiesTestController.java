package com.lee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lee.model.BookBean;

@RestController
@RequestMapping("properties")
public class PropertiesTestController {
	
	@Autowired
    private BookBean bookBean;

	@GetMapping
	public BookBean index() {
		return bookBean;
	}
	
}
