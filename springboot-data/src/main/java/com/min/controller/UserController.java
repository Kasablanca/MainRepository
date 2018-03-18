package com.min.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.min.entity.User;
import com.min.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired UserService userService;

	@GetMapping()
	public ResponseEntity<Page<User>> getUsers(Pageable pageable){
		return new ResponseEntity<Page<User>>(userService.getUsers(pageable), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Integer id){
		userService.deleteUser(id);
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	
}
