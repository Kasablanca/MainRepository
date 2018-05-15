package com.min.serversocket;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UdpDisgardServerWithChannels {
	
	private static final int PORT = 1009;
	private static final int MAX_PACKET_SIZE = 65507;

	public static void main(String[] args) {
		try {
			DatagramChannel channel = DatagramChannel.open();
			DatagramSocket socket = channel.socket();
			SocketAddress address = new InetSocketAddress(PORT);
			socket.bind(address);
			ByteBuffer buffer = ByteBuffer.allocate(MAX_PACKET_SIZE);
			while(true) {
				SocketAddress client = channel.receive(buffer);
				buffer.flip();
				System.out.println(client + " says ");
				while(buffer.hasRemaining()) System.out.print(buffer.get());
				System.out.println();
				buffer.clear();
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
