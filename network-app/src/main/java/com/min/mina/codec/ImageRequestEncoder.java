package com.min.mina.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ImageRequestEncoder extends ProtocolEncoderAdapter {

	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		ImageRequest request = (ImageRequest) message;
		IoBuffer buffer = IoBuffer.allocate(12, false);
		buffer.putInt(request.getWidth());
		buffer.putInt(request.getHeight());
		buffer.putInt(request.getNumberOfCharacters());
		buffer.flip();
		out.write(buffer);
	}

}
