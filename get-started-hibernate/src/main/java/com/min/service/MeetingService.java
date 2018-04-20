package com.min.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.dao.entity.Meeting;
import com.min.dao.repository.MeetingRepository;

@Service
public class MeetingService {

	@Autowired
	private MeetingRepository meetingRepository;
	
	public List<Meeting> getAllMeeting(){
		return meetingRepository.getAllMeeting();
	}
	
	public Meeting getById(Integer meetingId) {
		return meetingRepository.getById(meetingId);
	}
	
}
