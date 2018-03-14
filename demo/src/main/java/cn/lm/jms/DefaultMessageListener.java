package cn.lm.jms;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

@Component
public class DefaultMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		System.out.println(message);
	}

}
