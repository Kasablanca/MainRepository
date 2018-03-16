package com.min.netty.udt.echo.rendezvous;

import java.net.InetSocketAddress;

import com.min.netty.udt.echo.rendezvousBytes.Config;

import io.netty.util.internal.SocketUtils;

public class MsgEchoPeerOne extends MsgEchoPeerBase {
	
	public MsgEchoPeerOne(final InetSocketAddress self, final InetSocketAddress peer, final int messageSize) {
		super(self, peer, messageSize);
	}
	
	public static void main(final String[] args) throws Exception {
		final int messageSize = 64 * 1024;
		final InetSocketAddress self = SocketUtils.socketAddress(Config.hostOne, Config.portOne);
		final InetSocketAddress peer = SocketUtils.socketAddress(Config.hostTwo, Config.portTwo);
		new MsgEchoPeerOne(self, peer, messageSize).run();
	}
	
}
