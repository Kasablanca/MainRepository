package com.min.mina.ssl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import javax.net.ssl.SSLContext;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.min.mina.tcp.server.TimeServerHandler;

public class Test {
	private static final int PORT = 9123;

	public static void main(String[] args) throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();
		
		DefaultIoFilterChainBuilder chain  = acceptor.getFilterChain();
		chain.addLast("logger", new LoggingFilter());
		chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		chain.addFirst("ssl", new SslFilter(getDefaultSSLContext()));
		
		acceptor.setHandler(new TimeServerHandler());
		
		IoSessionConfig config = acceptor.getSessionConfig();
		config.setReadBufferSize(2048);
		config.setIdleTime(IdleStatus.BOTH_IDLE, 10);
		
		acceptor.bind(new InetSocketAddress(PORT));
	}
	
	private static SSLContext getDefaultSSLContext() {
		SSLContext sslContext = null;
		try {
		    sslContext = SSLContext.getInstance("TLSv1.2");
		    sslContext.init(null,null,null);
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
		return sslContext;
	}
	
}
