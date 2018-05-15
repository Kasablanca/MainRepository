package com.min.netty.udt.echo.bytes;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.udt.nio.NioUdtProvider;

@Sharable
public class ByteEchoServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		System.err.println("ECHO active " + NioUdtProvider.socketUDT(ctx.channel()).toStringOptions());
	}
	
	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) {
		ctx.write(msg);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}