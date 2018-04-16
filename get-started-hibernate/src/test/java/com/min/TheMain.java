package com.min;

import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.min.dao.entity.User;

public class TheMain {

	public static void main(String[] args) {
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try {
			SessionFactory sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
			
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			session.save(new User(UUID.randomUUID().toString(),"MR.LEE",(byte)0));
			
			session.getTransaction().commit();
			session.close();
			sessionFactory.close();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
			e.printStackTrace();
		}
	}

}
