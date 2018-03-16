package com.min.mina.tcp.client;

import org.apache.mina.filter.codec.demux.MessageEncoder;

public abstract class AbstractMessageEncoder<T extends AbstractMessage> implements MessageEncoder<T> {
	
}
