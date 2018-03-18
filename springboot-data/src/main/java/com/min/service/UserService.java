package com.min.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.min.dao.UserRepository;
import com.min.entity.User;

@Service
@Transactional
public class UserService {

	@Autowired UserRepository userRepository;
	
	public Page<User> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	public void deleteUser(Integer id) {
		if(id.equals(3))
			throw new RuntimeException("too bad!");
		userRepository.deleteById(id);
	}
	
}
