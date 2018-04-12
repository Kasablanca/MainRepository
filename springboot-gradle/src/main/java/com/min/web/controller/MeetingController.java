package com.min.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.min.dao.entity.Meeting;
import com.min.service.MeetingService;

@RestController
@RequestMapping("meeting")
public class MeetingController {

	@Autowired
	private MeetingService meetingService;
	
	@RequestMapping("save")
	public Meeting save(Meeting meeting, @RequestParam("files") MultipartFile[] files) {
		return meetingService.save(meeting,files);
	}
	
	@RequestMapping("findAll1")
	public Page<Meeting> findAll(Pageable pageable){
		return meetingService.findAll(pageable);
	}
	
	@RequestMapping("findAll2")
	public List<Meeting> findAll(Sort sort){
		return meetingService.findAll(sort);
	}

	@RequestMapping("findAll3")
	public List<Meeting> findAll(Integer[] ids) {
		return meetingService.findAll(Arrays.asList(ids));
	}
	
	@RequestMapping("findOne1")
	public Meeting findOne(Integer id) {
		return meetingService.findOne(id);
	}
	
	@RequestMapping("delete1")
	public void delete(Integer id) {
		meetingService.delete(id);
	}
	
}
