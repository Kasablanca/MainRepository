package com.min.dao.repository;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.min.Application;
import com.min.dao.entity.User;
import com.min.dao.repository.UserRepository;
import com.min.utils.RandomUtils;

import junit.framework.TestCase;

@Import(Application.class)
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest extends TestCase {

	@Autowired
	private UserRepository userRepository;
	
	//@Test
	public void add() {
		Date now = new Date();
		
		for(int i= 0;i<20;i++) {
			User user = new User();
			user.setAccount(RandomUtils.randomAccountName(12,8));
			user.setAddAcc("system");
			user.setAddTime(now);
			user.setLinkMail(RandomUtils.randomEmail(12, 5));
			user.setLinkPhone(RandomUtils.randomPhone());
			user.setLinkQq(RandomUtils.randomQQ(9));
			user.setUpdAcc("system");
			user.setUpdTime(now);
			user.setUserAge(20+new Random().nextInt(10));
			user.setUserAvatarUrl("");
			user.setUserId(UUID.randomUUID().toString());
			user.setUserNick(RandomUtils.randomChineseName());
			user.setUserSex((byte)new Random().nextInt(2));
			user.setVerNo(1);
			
			userRepository.saveAndFlush(user);
		}
	}
	
	//@Test
	public void queryByExample() throws JsonProcessingException {
		User user = new User();
		user.setLinkMail("@qq.com");
		
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("linkMail", match -> match.startsWith());
		Example<User> example = Example.of(user, matcher);
		
		Page<User> userList = userRepository.findAll(example, PageRequest.of(0,100));
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(userList));
	}
	
	//@Test
	public void query() throws JsonProcessingException {
		//List<User> userList = userRepository.findByAndSort((byte) 0, JpaSort.unsafe("LENGTH(userNick)"));
		List<User.UserProjection> userList = userRepository.findByUserSex((byte)1);
		
		System.out.println(/*new ObjectMapper().writeValueAsString(userList)*/userList.get(0).getClass().getCanonicalName());
	}
	
	@Test
	@Transactional
	public void delete(){
		userRepository.deleteAll();
		userRepository.flush();
	}
	
	//@Test
	@Transactional
	public void update() {
		int count = userRepository.updateSex("46492b44-4dc8-4be3-874f-3e6d95068def", (byte) 1);
		System.out.println(count);
	}
	
}
