package com.min.someapp.jms;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

public class DefaultSessionAwareMessageListener implements SessionAwareMessageListener<TextMessage> {

	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		System.out.println("receive"+message.getText());
	}

}
