package cn.lm.test;

import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.ProxyFactory;

import cn.lm.service.UserService;
import cn.lm.service.impl.UserAroundAdvice;
import cn.lm.service.impl.UserBeforAfterAdvice;
import cn.lm.service.impl.UserServiceImpl;

public class TestCase {
	public static void main(String[] args) {
		//case4();
		//WebApplicationContext w;
		//ResponseBodyAdvice<?> r;
		//ResponseEntity<?> rsp;
		Object s=null;
		System.out.println(StringUtils.isNotBlank((CharSequence) s));
	}
	
	public static void case1(){
		ProxyFactory proxyFactory = new ProxyFactory();
		UserService userService = new UserServiceImpl();
		proxyFactory.setTarget(userService);
		UserBeforAfterAdvice advice = new UserBeforAfterAdvice();
		proxyFactory.addAdvice(advice);
		UserService proxy = (UserService) proxyFactory.getProxy();
		proxy.sayHelloWorld();
	}
	
	public static void case2(){
		ProxyFactory proxyFactory = new ProxyFactory();
		UserService userService = new UserServiceImpl();
		proxyFactory.setTarget(userService);
		UserAroundAdvice advice = new UserAroundAdvice();
		proxyFactory.addAdvice(advice);
		UserService proxy = (UserService) proxyFactory.getProxy();
		proxy.sayHelloWorld();
	}
	
	public static void case3(){
		//ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		//UserService userService = (UserService) ctx.getBean("loveProxy");
		//userService.sayHelloWorld();
		
		//Love love = (Love) userService;
		//love.display("mr.lee");
	}
	
	public static void case4(){
		for(Entry<Object,Object> entry:System.getProperties().entrySet()){
			System.out.println(entry);
		}
	}
}
