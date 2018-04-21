package com.min.dao.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Meeting.class)
public abstract class Meeting_ {

	public static volatile SingularAttribute<Meeting, String> meetingName;
	public static volatile SetAttribute<Meeting, Data> datas;
	public static volatile SingularAttribute<Meeting, Date> expectedStartTime;
	public static volatile SetAttribute<Meeting, User> members;
	public static volatile SingularAttribute<Meeting, Integer> meetingId;
	public static volatile SingularAttribute<Meeting, Byte> status;

}

