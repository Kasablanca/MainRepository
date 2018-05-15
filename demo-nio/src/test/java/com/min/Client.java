package com.min;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class Client {

	public static void main(String[] args) throws IOException, InterruptedException {
		Socket socket = new Socket("192.168.2.4",10086);
		//socket.bind(new InetSocketAddress(10087));
		socket.getOutputStream().write(5);
		InputStream in = socket.getInputStream();
		byte[] buf = new byte[in.available()];
		Thread.sleep(2000);
		in.read(buf);
		System.out.println(buf[0]);
		socket.close();
	}
	
	public static void test1() throws IOException {
		SocketAddress addr = new InetSocketAddress("rama.poly.edu", 10086);
		SocketChannel client = SocketChannel.open(addr);
		client.configureBlocking(false);
		ByteBuffer buffer = ByteBuffer.allocate(74);
		WritableByteChannel output = Channels.newChannel(System.out);
		
		while(true) {
			int bytesRead = client.read(buffer);
			if(bytesRead > 0) {
				buffer.flip();
				output.write(buffer);
				buffer.clear();
			} else {
				System.out.println("connect fail");
				break;
			}
		}
	}

}
