package com.min.dao.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Data.class)
public abstract class Data_ {

	public static volatile SingularAttribute<Data, Integer> dataId;
	public static volatile SingularAttribute<Data, Integer> verNo;
	public static volatile SingularAttribute<Data, String> dataName;
	public static volatile SingularAttribute<Data, String> dataDownloadUrl;
	public static volatile SingularAttribute<Data, Byte> status;

}

