package com.min.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import com.min.dao.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	@Nullable
	List<User> findAllByLinkMailEndsWith(@Nullable String linkMail);

}
