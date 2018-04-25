package com.min.dao.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Meeting.class)
public abstract class Meeting_ {

	public static volatile SingularAttribute<Meeting, String> meetingNo;
	public static volatile SingularAttribute<Meeting, Date> actualStartTime;
	public static volatile SingularAttribute<Meeting, Date> addTime;
	public static volatile SingularAttribute<Meeting, Integer> expectedJoinNumber;
	public static volatile SetAttribute<Meeting, Data> datas;
	public static volatile SingularAttribute<Meeting, String> updAcc;
	public static volatile SingularAttribute<Meeting, Integer> meetingId;
	public static volatile SingularAttribute<Meeting, Integer> verNo;
	public static volatile SingularAttribute<Meeting, String> remark;
	public static volatile SingularAttribute<Meeting, Integer> meetingDuration;
	public static volatile SingularAttribute<Meeting, String> meetingAddress;
	public static volatile SingularAttribute<Meeting, Byte> roomStatus;
	public static volatile SingularAttribute<Meeting, String> meetingName;
	public static volatile SingularAttribute<Meeting, Date> expireTime;
	public static volatile SingularAttribute<Meeting, Byte> openFlag;
	public static volatile SingularAttribute<Meeting, String> resourceUrl;
	public static volatile SingularAttribute<Meeting, Byte> useFlag;
	public static volatile SingularAttribute<Meeting, String> addAcc;
	public static volatile SingularAttribute<Meeting, Date> updTime;
	public static volatile SingularAttribute<Meeting, Date> expectedStartTime;
	public static volatile SetAttribute<Meeting, User> members;
	public static volatile SingularAttribute<Meeting, Integer> actualJoinNumber;
	public static volatile SingularAttribute<Meeting, User> user;
	public static volatile SingularAttribute<Meeting, Byte> status;

}

