package com.min.mina.tcp.client;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class AddMessageEncoder<T extends AddMessage> extends AbstractMessageEncoder<T> {

	@Override
	public void encode(IoSession session, T message, ProtocolEncoderOutput out) throws Exception {
		
	}

}
