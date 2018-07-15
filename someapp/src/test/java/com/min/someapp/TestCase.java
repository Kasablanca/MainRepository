package com.min.someapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.min.someapp.dao.ConnectionManager;
import com.min.someapp.service.system.UserService;

@Transactional
@SpringBootTest
@Import(Application.class)
@RunWith(SpringRunner.class)
public class TestCase {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConnectionManager connectionManager;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void time() throws JsonProcessingException {
		objectMapper.setTimeZone(TimeZone.getDefault());
		System.out.println(objectMapper.writeValueAsString(new Date(1531635474000L)));
	}

	//@Test
	public void query() {
		userService.getById(110);
	}
	
	//@Test
	public void init() throws SQLException {
		Connection conn = connectionManager.getConnection("jdbc:mysql://127.0.0.1:3306/server3?characterEncoding=UTF-8");
		conn.setAutoCommit(false);
		PreparedStatement stmt = conn.prepareStatement("insert into t_loginlog(login_time,pid,duration) values(?,?,?)");
		long seed = new Date().getTime();
		Random random = new Random(seed);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for(int i = 0; i < 1000000; i++) {
			stmt.setTimestamp(1, Timestamp.valueOf(sdf.format(new Date(seed-random.nextInt(100000000)))));
			stmt.setInt(2, random.nextInt(1000000));
			stmt.setInt(3, random.nextInt(3600*6));
			int count = stmt.executeUpdate();
			if(count != 1) {
				System.out.println("insert error");
			}
		}
		conn.commit();
	}
	
}
