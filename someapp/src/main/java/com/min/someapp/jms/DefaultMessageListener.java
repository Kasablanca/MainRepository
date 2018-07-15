package com.min.someapp.jms;

import javax.jms.Message;
import javax.jms.MessageListener;

//@Component
public class DefaultMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		System.out.println(message);
	}

}
