package com.min.dao.repository;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.min.Application;
import com.min.dao.entity.Data;
import com.min.dao.entity.Meeting;
import com.min.dao.entity.User;

//@Transactional
@SpringBootTest
@Import(Application.class)
@RunWith(SpringRunner.class)
public class TestCase {

	@Autowired
	private DataRepository dataRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MeetingRepository meetingRepository;
	
	@Test
	public void dataRepositoryAdd() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		User user = new User();
		user.setUserId("userId");
		user.setUserNick("userNick");
		user.setVerNo(-1);
		user.setAddAcc("addAcc");
		user.setAddTime(new Date());
		/*User newUser = */userRepository.saveAndFlush(user);
		
		Data data = new Data();
		data.setAddAcc("addAcc");
		data.setAddTime(new Date());
		data.setDataDownloadUrl("dataDownLocaUrl");
		data.setDataLevel((byte)-1);
		data.setDataName("dataname");
		data.setDataType((byte)-1);
		data.setDataVolume(-1);
		data.setEffectiveTime(new Date());
		data.setEncryptFlag((byte)-1);
		data.setPublicFlag((byte)-1);
		data.setFailureTime(new Date());
		data.setRemark("remark");
		data.setUpdAcc("updAcc");
		data.setUpdTime(new Date());
		data.setUploadedTime(new Date());
		data.setUseFlag((byte)-1);
		data.setUser(user);
		//data.setUserId("userId2");
		data.setVerNo(-1);
		Data newData = dataRepository.saveAndFlush(data);
		
		System.out.println(mapper.writeValueAsString(newData));
	}
	
	//@Test
	public void dataRepositoryUpdate() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		User user = new User();
		user.setUserId("ff808081626fda6501628937bf8b0003");
		user.setUserNick("userNick");
		user.setVerNo(-1);
		user.setAddAcc("addAcc");
		user.setAddTime(new Date());
		User newUser = userRepository.saveAndFlush(user);
		
		Data data = new Data();
		data.setDataId(173);
		data.setAddAcc("addAcc");
		data.setAddTime(new Date());
		data.setDataDownloadUrl("dataDownLocaUrl");
		data.setDataLevel((byte)-1);
		data.setDataName("dataname");
		data.setDataType((byte)-1);
		data.setDataVolume(-1);
		data.setEffectiveTime(new Date());
		data.setEncryptFlag((byte)-1);
		data.setPublicFlag((byte)-1);
		data.setFailureTime(new Date());
		data.setRemark("remark");
		data.setUpdAcc("updAcc");
		data.setUpdTime(new Date());
		data.setUploadedTime(new Date());
		data.setUseFlag((byte)-1);
		data.setUser(newUser);
		data.setVerNo(-1);
		Data newData = dataRepository.saveAndFlush(data);
		
		System.out.println(mapper.writeValueAsString(newData));
	}
	
	//@Test
	public void dataRepositoryDelete() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		dataRepository.deleteById(173);
		dataRepository.flush();
		Data data = dataRepository.getOne(173);
		
		System.out.println(mapper.writeValueAsString(data));
	}
	
	//@Test
	public void meetingRepositoryAdd() {
		Meeting meeting = new Meeting();
		meeting.setMeetingName(com.min.utils.RandomUtils.randomChineseName());
		meeting.setUser(userRepository.findAll(PageRequest.of(new Random().nextInt((int) userRepository.count()), 1)).getContent().get(0));
		
		meetingRepository.saveAndFlush(meeting);
	}
	
}
