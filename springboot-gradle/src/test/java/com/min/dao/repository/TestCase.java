package com.min.dao.repository;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.min.Application;
import com.min.dao.entity.Data;
import com.min.dao.entity.Meeting;
import com.min.dao.entity.User;
import com.min.utils.RandomUtils;

@Transactional
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
	
	//@Test
	public void dataRepositoryAdd() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
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
	
	//@Test
	public void initDatabaseData() {
		Date now = new Date();
		
		for (int i = 0; i < 50; i++) {
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
			User newUser = userRepository.saveAndFlush(user);
			
			Data data = new Data();
			data.setAddAcc("system");
			data.setAddTime(new Date());
			data.setDataDownloadUrl("dataDownLocaUrl");
			data.setDataLevel((byte)-1);
			data.setDataName(RandomUtils.getRandomJianHan(5));
			data.setDataType((byte)-1);
			data.setDataVolume(-1);
			data.setEffectiveTime(now);
			data.setEncryptFlag((byte)-1);
			data.setPublicFlag((byte)-1);
			data.setFailureTime(now);
			data.setRemark(RandomUtils.getRandomJianHan(50));
			data.setUpdAcc("system");
			data.setUpdTime(now);
			data.setUploadedTime(now);
			data.setUseFlag((byte)-1);
			int rand = new Random().nextInt(2);
			if(rand == 0) {
				data.setUser(userRepository.findAll(
						PageRequest.of(new Random().nextInt((int) userRepository.count()), 1))
						.getContent().get(0));
			} else {
				data.setUser(newUser);
			}
			data.setVerNo(1);
			Data newData = dataRepository.saveAndFlush(data);
			
			Meeting meeting = new Meeting();
			meeting.setActualJoinNumber(null);
			meeting.setActualStartTime(null);
			meeting.setAddAcc("system");
			meeting.setAddTime(now);
			rand = new Random().nextInt(3);
			if(rand == 0) {
				meeting.setDatas(new HashSet<Data>());
			} else if(rand == 1) {
				meeting.setDatas(Collections.singleton(newData));
			} else {
				meeting.setDatas(new HashSet<>(dataRepository.findAll(
						PageRequest.of(new Random().nextInt((int) dataRepository.count()), new Random().nextInt(5)+5))
						.getContent()));
			}
			meeting.setExpectedJoinNumber(meeting.getDatas().size());
			meeting.setExpectedStartTime(now);
			meeting.setExpireTime(now);
			meeting.setMeetingAddress(RandomUtils.getRandomJianHan(50));
			meeting.setMeetingDuration(new Random().nextInt(50)+50);
			meeting.setMeetingName(RandomUtils.getRandomJianHan(3));
			meeting.setMeetingNo(null);
			meeting.setOpenFlag((byte)1);
			meeting.setRemark(RandomUtils.getRandomJianHan(50));
			meeting.setResourceUrl(RandomUtils.randomAccountName(50, 0));
			meeting.setRoomStatus((byte)1);
			meeting.setStatus((byte)1);
			meeting.setUpdAcc("system");
			meeting.setUpdTime(now);
			meeting.setUseFlag((byte)1);
			rand = new Random().nextInt(2);
			if(rand == 0) {
				meeting.setUser(newUser);
			} else {
				meeting.setUser(userRepository.findAll(
						PageRequest.of(new Random().nextInt((int) userRepository.count()), 1))
						.getContent().get(0));
			}
			rand = new Random().nextInt(3);
			if(rand == 0) {
				meeting.setMembers(new HashSet<User>());
			} else if(rand == 1) {
				meeting.setMembers(Collections.singleton(newUser));
			} else {
				meeting.setMembers(new HashSet<User>(userRepository.findAll(
						PageRequest.of(new Random().nextInt((int) userRepository.count()), new Random().nextInt(5)+5))
						.getContent()));
			}
			meeting.setVerNo(1);
			meetingRepository.saveAndFlush(meeting);
		}
	}
	
	@Test
	public void query() throws JsonProcessingException {
		Meeting meeting = meetingRepository.getOne(77);
		ObjectMapper writer = new ObjectMapper();
		writer.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		System.out.println(writer.writeValueAsString(meeting));
	}
	
}
