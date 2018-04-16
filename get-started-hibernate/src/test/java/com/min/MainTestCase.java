package com.min;

import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;

import com.min.dao.entity.User;

import junit.framework.TestCase;


public class MainTestCase extends TestCase {
	
	private SessionFactory sessionFactory;

	public void setUp() throws Exception {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			StandardServiceRegistryBuilder.destroy( registry );
			e.printStackTrace();
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		sessionFactory.close();
	}
	
	@Test
	public void test1() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.save(new User(UUID.randomUUID().toString(),"MS.LEE",(byte)1));
		
		session.getTransaction().commit();
		session.close();
	}
	
	
	
}
