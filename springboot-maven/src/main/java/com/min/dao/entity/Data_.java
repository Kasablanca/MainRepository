package com.min.dao.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Data.class)
public abstract class Data_ {

	public static volatile SingularAttribute<Data, Date> addTime;
	public static volatile SingularAttribute<Data, Date> effectiveTime;
	public static volatile SingularAttribute<Data, String> updAcc;
	public static volatile SingularAttribute<Data, Byte> dataType;
	public static volatile SingularAttribute<Data, Date> uploadedTime;
	public static volatile SingularAttribute<Data, Byte> publicFlag;
	public static volatile SingularAttribute<Data, Integer> verNo;
	public static volatile SingularAttribute<Data, String> dataName;
	public static volatile SingularAttribute<Data, String> remark;
	public static volatile SingularAttribute<Data, Date> failureTime;
	public static volatile SingularAttribute<Data, Byte> encryptFlag;
	public static volatile SingularAttribute<Data, String> encryptPwd;
	public static volatile SingularAttribute<Data, Integer> dataId;
	public static volatile SingularAttribute<Data, Byte> useFlag;
	public static volatile SingularAttribute<Data, String> addAcc;
	public static volatile SingularAttribute<Data, Date> updTime;
	public static volatile SingularAttribute<Data, Integer> dataVolume;
	public static volatile SingularAttribute<Data, String> dataDownloadUrl;
	public static volatile SingularAttribute<Data, String> encryptAccount;
	public static volatile SingularAttribute<Data, User> user;
	public static volatile SingularAttribute<Data, Byte> dataLevel;

}

