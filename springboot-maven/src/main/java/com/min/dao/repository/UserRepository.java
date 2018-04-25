package com.min.dao.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import com.min.dao.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	@Nullable
	List<User> findAllByLinkMailEndsWith(@Nullable String linkMail);

	@Nullable
	List<User> findByLinkMail(@Nullable String linkMail);
	
	@Query("select u from User u where u.userSex = ?1")
	List<User> findByAndSort(byte sex,Sort sort);
	
	@Modifying
	@Query("update User u set u.userSex = ?2 where u.userId = ?1")
	int updateSex(String userId, byte sex);
	
	List<User.UserProjection> findByUserSex(Byte sex);
	
	User findByUserNickAndLinkMail(String userNick,String linkMail);

}
