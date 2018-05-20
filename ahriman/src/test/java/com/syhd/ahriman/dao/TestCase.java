package com.syhd.ahriman.dao;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.syhd.ahriman.Application;
import com.syhd.ahriman.dao.mapper.UserMapper;
import com.syhd.ahriman.dao.model.User;
import com.syhd.ahriman.utils.RandomUtils;

@SpringBootTest
@Import(Application.class)
@RunWith(SpringRunner.class)
public class TestCase {
	
	@Autowired
	UserMapper userMapper;
	
	@SuppressWarnings("deprecation")
	@Test
	public void initData() {
		for(int i = 1; i <= 1000; i++) {
			User user = new User();
			user.setId(i);
			user.setAge(20+new Random().nextInt(10));
			Date birthday = new Date();
			birthday.setYear(100+new Random().nextInt(10));
			user.setBirthday(birthday);
			user.setEmail(RandomUtils.randomEmail(6, 3));
			user.setSex((byte) (1+new Random().nextInt(2)));
			user.setUsername(RandomUtils.randomChineseName());
			
			userMapper.insert(user);
		}
	}

}
