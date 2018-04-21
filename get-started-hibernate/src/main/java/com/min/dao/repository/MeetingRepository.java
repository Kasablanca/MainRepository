package com.min.dao.repository;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.min.dao.entity.Meeting;

@Repository
public class MeetingRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	public List<Meeting> getAllMeeting(){
		return sessionFactory.getCurrentSession().
				createQuery("select m from Meeting m", Meeting.class).getResultList();
	}
	
	public Meeting getById(Integer meetingId) {
		return sessionFactory.getCurrentSession().get(Meeting.class, 1);
	}
	
}
