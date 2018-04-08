package cn.lm.listener;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class OnlineCountListener implements HttpSessionListener {
	private static final AtomicInteger onlineCount = new AtomicInteger(0);

    public OnlineCountListener() {
    	System.out.println(this.getClass().getName()+" constructor");
    }

    public void sessionCreated(HttpSessionEvent se)  { 
    	System.out.println("当前在线人数："+onlineCount.addAndGet(1));
    }

    public void sessionDestroyed(HttpSessionEvent se)  { 
    	System.out.println("当前在线人数："+onlineCount.decrementAndGet());
    }
	
}
