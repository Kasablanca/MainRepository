package com.min.springbootdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.min.springbootdemo.service.UserService;

@Transactional
@SpringBootTest
@Import(Application.class)
@RunWith(SpringRunner.class)
public class TestCase {
	
	@Autowired
	private UserService userService;

	@Test
	public void query() {
		userService.findById(36);
	}
	
}
