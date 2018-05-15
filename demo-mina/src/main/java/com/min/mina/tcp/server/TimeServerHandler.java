package com.min.mina.tcp.server;


import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TimeServerHandler extends IoHandlerAdapter {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String str = message.toString();
		System.out.println("message Received:" + message);
		if( str.trim().equalsIgnoreCase("quit") ) {
			session.closeOnFlush();
			return;
		}
		Date date = new Date();
		session.write( date.toString() );
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println( "IDLE " + session.getIdleCount( status ));
	}

}
