package com.min.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.min.dao.entity.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

}
