package com.syhd.ahriman;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.syhd.ahriman.dao.mapper.AppServerMapper;

@Transactional
@SpringBootTest
@Import(Application.class)
@RunWith(SpringRunner.class)
public class TestCase {
	
	@Autowired
	private AppServerMapper appServerMapper;
	
	@Test
	public void query() throws JsonProcessingException {
		appServerMapper.selectByPrimaryKey(1);
	}
	
}
