package com.min.netty.http.snoop;

import java.net.URI;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.cookie.ClientCookieEncoder;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

public final class HttpSnoopClient {
	static final String URL = System.getProperty("url", "http://127.0.0.1:8080/");
	public static void main(String[] args) throws Exception {
		URI uri = new URI(URL);
		String scheme = uri.getScheme() == null? "http" : uri.getScheme();
		String host = uri.getHost() == null? "127.0.0.1" : uri.getHost();
		int port = uri.getPort();
		if (port == -1) {
			if ("http".equalsIgnoreCase(scheme)) {
				port = 80;
			} else if ("https".equalsIgnoreCase(scheme)) {
				port = 443;
			}
		}
		if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
			System.err.println("Only HTTP(S) is supported.");
			return;
		}
		// Configure SSL context if necessary.
		final boolean ssl = "https".equalsIgnoreCase(scheme);
		final SslContext sslCtx;
		if (ssl) {
			sslCtx = SslContextBuilder.forClient()
					.trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		} else {
			sslCtx = null;
		}
		// Configure the client.
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.handler(new HttpSnoopClientInitializer(sslCtx));
			// Make the connection attempt.
			Channel ch = b.connect(host, port).sync().channel();
			// Prepare the HTTP request.
			HttpRequest request = new DefaultFullHttpRequest(
					HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());
			request.headers().set(HttpHeaderNames.HOST, host);
			request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
			request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);
			// Set some example cookies.
			request.headers().set(
					HttpHeaderNames.COOKIE,
					ClientCookieEncoder.STRICT.encode(
							new DefaultCookie("my-cookie", "foo"),
							new DefaultCookie("another-cookie", "bar")));
			// Send the HTTP request.
			ch.writeAndFlush(request);
			// Wait for the server to close the connection.
			ch.closeFuture().sync();
		} finally {
			// Shut down executor threads to exit.
			group.shutdownGracefully();
		}
	}
}
