package com.min.mina.tcp.client;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

public class SumUpProtocolCodecFactory extends DemuxingProtocolCodecFactory {

	public SumUpProtocolCodecFactory(boolean server) {
		if (server) {
			super.addMessageDecoder(AddMessageDecoder.class);
			super.addMessageEncoder(ResultMessage.class, ResultMessageEncoder.class);
		} else // Client
		{
			super.addMessageEncoder(AddMessage.class, AddMessageEncoder.class);
			super.addMessageDecoder(ResultMessageDecoder.class);
		}
	}

}
