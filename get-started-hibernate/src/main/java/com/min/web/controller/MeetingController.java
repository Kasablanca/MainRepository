package com.min.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.dao.entity.Meeting;
import com.min.service.MeetingService;

@RestController
@RequestMapping("meeting")
public class MeetingController {

	@Autowired
	private MeetingService meetingService;
	
	@RequestMapping("all")
	public List<Meeting> allMeeting(){
		return meetingService.getAllMeeting();
	}
	
	@RequestMapping("one/{id}")
	public Meeting getById(@PathVariable("id")Integer meetingId) {
		return meetingService.getById(meetingId);
	}
	
}
