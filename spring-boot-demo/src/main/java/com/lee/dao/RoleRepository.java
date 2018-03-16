package com.lee.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lee.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
