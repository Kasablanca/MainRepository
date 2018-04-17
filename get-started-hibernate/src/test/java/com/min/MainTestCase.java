package com.min;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.engine.spi.SessionImplementor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.min.dao.entity.Data;
import com.min.dao.entity.Meeting;
import com.min.dao.entity.User;


public class MainTestCase {
	
	private static SessionFactory sessionFactory;
	private Session session;

	@BeforeClass
	public static void setUp() throws Exception {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			StandardServiceRegistryBuilder.destroy( registry );
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		sessionFactory.close();
	}
	
	@Before
	public void openSessionAndBeginTransaction() {
		session = sessionFactory.openSession();
		session.beginTransaction();
	}
	
	@After
	public void commitTransactionAndCloseSession() {
		//session.getTransaction().commit();
		session.close();
	}
	
	//@Test
	public void test1() {
		session.save(new User(UUID.randomUUID().toString(),"MR.LEE",(byte)0));
	}
	
	//@Test
	public void test2() {
		Data data = new Data();
		data.setDataName("data1");
		data.setVerNo(2);
		data.setUser(session.byId(User.class).getReference("ef4e8e18-043d-409c-992e-a85c135784e0"));
		session.save(data);
	}
	
	//@Test
	public void test3() {
		Set<Data> datas = new HashSet<>();
		datas.add(session.byId(Data.class).getReference(1));
		datas.add(session.byId(Data.class).getReference(2));
		
		Set<User> users = new HashSet<>();
		users.add(session.byId(User.class).getReference("bbe20287-511a-473e-82b7-0e5afeed6e7f"));
		users.add(session.byId(User.class).getReference("ef4e8e18-043d-409c-992e-a85c135784e0"));
		
		Meeting meeting = new Meeting();
		meeting.setMeetingName("meeting1");
		meeting.setDatas(datas);
		meeting.setMembers(users);
		meeting.setUser(users.iterator().next());
		session.save(meeting);
	}
	
	@Test
	public void test4() {
		User user = session.load(User.class, "ef4e8e18-043d-409c-992e-a85c135784e0");
		
		Assert.assertFalse(Hibernate.isInitialized(user));
		
		PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
		Assert.assertFalse(jpaUtil.isLoaded(user));
		
		Assert.assertNotNull(session.unwrap(Session.class));
		Assert.assertNotNull(session.unwrap(SessionImplementor.class));
		
		//session.getTransaction().commit();
	}
	
}
