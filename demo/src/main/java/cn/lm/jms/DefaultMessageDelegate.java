package cn.lm.jms;

import java.io.Serializable;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class DefaultMessageDelegate implements MessageDelegate {

	@Override
	public void handleMessage(String message) {
		System.out.println("String message:"+message);
	}

	@Override
	public void handleMessage(Map<?, ?> message) {
		System.out.println("Map message:"+message);
	}

	@Override
	public void handleMessage(byte[] message) {
		System.out.println("byte message:"+message);
	}

	@Override
	public void handleMessage(Serializable message) {
		System.out.println("Serializable message:"+message);
	}

}
