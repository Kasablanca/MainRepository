package com.min.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.dao.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
