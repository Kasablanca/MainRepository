package cn.lm.jms;

import java.util.UUID;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class DefaultMessageListenerService {

	@JmsListener(destination = "TomcatQueue")
	@SendTo("Default-Response-Queue")
	public Message<String> defaultProcess(String message) {
		System.out.println(this.getClass().getName()+" message:"+message);
		
		return MessageBuilder.withPayload(message).setHeader("code", UUID.randomUUID().toString()).build();
	}
	
}
