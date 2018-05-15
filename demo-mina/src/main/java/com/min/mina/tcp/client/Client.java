package com.min.mina.tcp.client;

import java.net.InetSocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class Client {
	private static final String HOSTNAME = "localhost";
	private static final int PORT = 8080;
	private static final long CONNECT_TIMEOUT = 30*1000L; // 30 seconds
	private static final boolean USE_CUSTOM_CODEC = true;

	public static void main(String[] args) throws InterruptedException {
		NioSocketConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);

		if (USE_CUSTOM_CODEC) {
			connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new SumUpProtocolCodecFactory(false)));
		} else {
			connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		}

		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.setHandler(new ClientSessionHandler(new int[0]));
		IoSession session;

		for (;;) {
			try {
				ConnectFuture future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
				future.awaitUninterruptibly();
				session = future.getSession();
				break;
			} catch (RuntimeIoException e) {
				System.err.println("Failed to connect.");
				e.printStackTrace();
				Thread.sleep(5000);
			}
		}

		// wait until the summation is done
		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}

}
