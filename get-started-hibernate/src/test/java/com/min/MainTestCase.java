package com.min;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.min.dao.entity.Data;
import com.min.dao.entity.Meeting;
import com.min.dao.entity.User;
import com.min.utils.RandomUtils;


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

	//@Test
	public void test4() {
		User user = session.load(User.class, "ef4e8e18-043d-409c-992e-a85c135784e0");

		Assert.assertFalse(Hibernate.isInitialized(user));

		PersistenceUtil jpaUtil = Persistence.getPersistenceUtil();
		Assert.assertFalse(jpaUtil.isLoaded(user));

		Assert.assertNotNull(session.unwrap(Session.class));
		Assert.assertNotNull(session.unwrap(SessionImplementor.class));

		//session.getTransaction().commit();
	}

	//@Test
	public void test5() {
		for(int i=0;i<50;i++) {
			User user = new User();
			user.setUserId(UUID.randomUUID().toString());
			user.setUserNick(RandomUtils.getRandomJianHan(3));
			user.setUserSex((byte) new Random().nextInt(2));
			session.save(user);
			System.out.println("saved!");
			if((i+1)%10 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	//@Test
	public void test6() {
		String hqlUpdate = "update User u set u.userNick = :userNick where userId = :userId";
		int updated = session.createQuery(hqlUpdate)
				.setParameter("userNick", "versioned")
				.setParameter("userId","39ed96a5-5127-4952-8578-bade101008c3")
				.executeUpdate();
		Assert.assertEquals(updated, 1);
	}

	//@Test
	public void test7() {
		String hqlUpdate = "update versioned Data d set d.dataName = :dataName where d.dataId = :dataId";
		int updated = session.createQuery(hqlUpdate)
				.setParameter("dataName", "versioned")
				.setParameter("dataId", 1)
				.executeUpdate();
		Assert.assertEquals(updated, 1);
	}

	//@Test
	public void test8() {
		String hqlDelete = "delete from User u where u.userId = :userId";
		int deleted = session.createQuery(hqlDelete)
				.setParameter("userId", "4dd5f8a5-2af7-41e5-b44d-044050a7fac9")
				.executeUpdate();
		Assert.assertEquals(deleted, 1);
	}

	//@Test
	public void test9() {
		System.out.println(session.getCurrentLockMode(
				session.get(User.class, "39ed96a5-5127-4952-8578-bade101008c3")));
	}

	//@Test
	public void test10() {
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("select u from User u where u.userId = :userId");
		query.setParameter("userId", "39ed96a5-5127-4952-8578-bade101008c3");
		query.setCacheable(true);
		query.list();
		query.list();
	}

	//@Test
	public void test11() {
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("select u from User u");
		query.list();

		User user = session.byId(User.class).load("39ed96a5-5127-4952-8578-bade101008c3");
		user = session.byId(User.class).load("39ed96a5-5127-4952-8578-bade101008c3");
		Assert.assertNotNull(user);
		Assert.assertTrue(session.contains(user));
	}

	//@Test
	@SuppressWarnings("unchecked")
	public void initDatabaseData() {
		Date now = new Date();

		for (int i = 0; i < 50; i++) {
			User user = new User();
			user.setUserId(UUID.randomUUID().toString());
			user.setUserNick(RandomUtils.randomChineseName());
			user.setUserSex((byte)new Random().nextInt(2));
			session.save(user);

			Data data = new Data();
			data.setDataDownloadUrl("dataDownLocaUrl");
			data.setDataName(RandomUtils.getRandomJianHan(5));
			int rand = new Random().nextInt(2);
			if(rand == 0) {
				List<?> users = session.createQuery("select u from User u").list();
				data.setUser((User) users.get(new Random().nextInt(users.size())));
			} else {
				data.setUser(user);
			}
			data.setVerNo(1);
			session.save(data);

			Meeting meeting = new Meeting();
			rand = new Random().nextInt(3);
			if(rand == 0) {
				meeting.setDatas(new HashSet<Data>());
			} else if(rand == 1) {
				meeting.setDatas(Collections.singleton(data));
			} else {
				List<?> datas =session.createQuery("select d from Data d").list();
				meeting.setDatas(new HashSet<>(
						(List<Data>)datas.subList(datas.size()-new Random().nextInt(datas.size()), datas.size())));
			}
			meeting.setExpectedStartTime(now);
			meeting.setMeetingName(RandomUtils.getRandomJianHan(3));
			meeting.setStatus((byte)1);

			rand = new Random().nextInt(2);
			if(rand == 0) {
				meeting.setUser(user);
			} else {
				List<?> users = session.createQuery("select u from User u").list();
				meeting.setUser((User) users.get(new Random().nextInt(users.size())));
			}
			rand = new Random().nextInt(3);
			if(rand == 0) {
				meeting.setMembers(new HashSet<User>());
			} else if(rand == 1) {
				meeting.setMembers(Collections.singleton(user));
			} else {
				List<?> members = session.createQuery("select u from User u").list();
				meeting.setMembers(new HashSet<User>(
						(List<User>)members.subList(members.size()-new Random().nextInt(members.size()), members.size())));
			}
			session.save(meeting);
		}
	}
	
	//@Test
	@SuppressWarnings("unchecked")
	public void test12() {
		List<Meeting> meeting = session.createQuery("select m from Meeting m left join fetch m.datas datas where m.meetingId = :meetingId")
				.setParameter("meetingId", 45)
				.list();
		for(Meeting meeting_:meeting) {
			System.out.println("datas:"+meeting_.getDatas()+",members:"+meeting_.getMembers());
		}
		Assert.assertNotNull(meeting);
		//Assert.assertEquals(meeting.size(), 1);
		Assert.assertNotNull(meeting.get(0));
		Assert.assertTrue(meeting.get(0) instanceof Meeting);
	}
	
	@Test
	public void test13() {
		/*String warranty = "My product warranty";

		final Product product = new Product();
		product.setId( 1 );
		product.setName( "Mobile phone" );

		session.doWork( connection -> {
		    product.setWarranty( ClobProxy.generateProxy( warranty ) );
		} );

		session.save( product );*/
		/*Product product = session.get(Product.class, 1);
		Assert.assertNotNull(product);*/
		
		Meeting meeting = session.get(Meeting.class, 1);
		System.out.println(meeting.getMeetingName());
		System.out.println(meeting.getUser());
		
		session.getTransaction().commit();
	}
	
	//@Test
	public void test14() {
		List<?> productList = session.createNativeQuery("select * from product").list();
		System.out.println(productList.size());
	}

}
