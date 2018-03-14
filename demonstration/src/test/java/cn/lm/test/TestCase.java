package cn.lm.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.RestTemplate;

import cn.lm.Result;

public class TestCase {

	public static void main(String[] args) {
		activemq();
	}
	
	public static void activemq() {
		ConnectionFactory factory;
		javax.jms.Connection conn;
		Session session;
		Destination dest;
		MessageConsumer consumer;
		factory = new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
		try {
			conn = factory.createConnection();
			conn.start();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			dest = session.createQueue("test-queue");
			consumer = session.createConsumer(dest);
			while(true) {
				Message msg = consumer.receive();
				System.out.println(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void webservice() {
		@SuppressWarnings("unused")
		ApplicationContext ctx = getSpringContext();
	}
	
	public static void jmsTemplate(){
		
	}
	
	public static void restTest(){
		RestTemplate restTmpl = getSpringContext().getBean(RestTemplate.class);
		Result result = restTmpl.getForObject("http://192.168.2.74:8081/demo/user", Result.class);
		List<?> userlist = (List<?>) result.getData();
		for(Object user:userlist){
			System.out.println(user);
		}
	}
	
	/**
	 * 获取spring上下文环境
	 * @return
	 */
	public static ApplicationContext getSpringContext(){
		return new ClassPathXmlApplicationContext("spring.xml");
	}

	/**
	 * 获取mybatis连接
	 * @return
	 * @throws IOException
	 */
	public static SqlSession getSqlSession() throws IOException{
		String resource = "mybatis-config-test.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		return sqlSessionFactory.openSession();
	}

	/**
	 * 获取jdbc连接
	 * @return
	 */
	public static Connection getJdbcConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://192.168.2.210:3306/itpms", "itpms", "itpms");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
