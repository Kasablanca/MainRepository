package com.min.netty.udt.echo.bytes;

import java.util.concurrent.ThreadFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.nio.NioUdtProvider;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

public final class ByteEchoClient {
	static final String HOST = System.getProperty("host", "127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
	static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));
	public static void main(String[] args) throws Exception {
		// Configure the client.
		final ThreadFactory connectFactory = new DefaultThreadFactory("connect");
		final NioEventLoopGroup connectGroup = new NioEventLoopGroup(1,
				connectFactory, NioUdtProvider.BYTE_PROVIDER);
		try {
			final Bootstrap boot = new Bootstrap();
			boot.group(connectGroup)
			.channelFactory(NioUdtProvider.BYTE_CONNECTOR)
			.handler(new ChannelInitializer<UdtChannel>() {
				@Override
				public void initChannel(final UdtChannel ch)
						throws Exception {
					ch.pipeline().addLast(
							new LoggingHandler(LogLevel.INFO),
							new ByteEchoClientHandler());
				}
			});
			// Start the client.
			final ChannelFuture f = boot.connect(HOST, PORT).sync();
			// Wait until the connection is closed.
			f.channel().closeFuture().sync();
		} finally {
			// Shut down the event loop to terminate all threads.
			connectGroup.shutdownGracefully();
		}
	}
}
