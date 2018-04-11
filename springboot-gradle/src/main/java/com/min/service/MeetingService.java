package com.min.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.min.dao.entity.Meeting;
import com.min.dao.repository.MeetingRepository;

@Service
@Transactional
public class MeetingService {
	
	private static final Logger logger = Logger.getLogger(MeetingService.class);

	@Autowired
	private MeetingRepository meetingRepository;
	
	public Meeting save(Meeting meeting) {
		if(meeting.getExpectedStartTime().compareTo(new Date()) < 0)
			throw new IllegalArgumentException("会议开始时间不能小于当前时间！");
		return meetingRepository.saveAndFlush(meeting);
	}
	
	public Page<Meeting> findAll(Pageable pageable){
		logger.debug("MeetingService Page<Meeting> findAll(Pageable pageable)");
		return meetingRepository.findAll(pageable);
	}
	
	public List<Meeting> findAll(Sort sort){
		logger.debug("MeetingService List<Meeting> findAll(Sort sort)");
		return meetingRepository.findAll(sort);
	}

	public List<Meeting> findAll(List<Integer> ids) {
		return meetingRepository.findAllById(ids);
	}
	
	public Meeting findOne(Integer id) {
		return meetingRepository.getOne(id);
	}
	
	public void delete(Integer id) {
		meetingRepository.deleteById(id);
	}
	
}
