package com.lee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lee.model.BookBean;

@RestController
@RequestMapping("properties")
public class PropertiesTestController {
	
	@Value("${book.author}")
	private String bookAuthor;
	
	@Value("${book.name}")
	private String bookName;
	
	@Value("${book.pinyin}")
	private String bookPinyin;
	
	@Autowired
    private BookBean bookBean;

	@GetMapping
	public BookBean index() {
		return bookBean;
	}
	
}
