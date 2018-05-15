package com.min;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.NetworkChannel;
import java.nio.channels.SocketChannel;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		test7();
	}
	
	public static void test1() throws IOException, InterruptedException {
		@SuppressWarnings("resource")
		RandomAccessFile file = new RandomAccessFile("C:\\Users\\MIN.LEE\\Desktop/删除注释.txt","r");
		FileChannel fileChannel = file.getChannel();
		MappedByteBuffer buffer = fileChannel.map(MapMode.READ_ONLY, 0, file.length());
		byte[] buf = new byte[(int) file.length()];
		while(true) {
			buffer.clear();
			buffer.get(buf,0,buffer.limit());
			System.out.println(new String(buf,"unicode"));
			Thread.sleep(5000);
		}
	}
	
	public static void test2() {
		int i = -1;
		System.out.printf("%d",((long)-1 << 32) ^ i);
		System.out.printf("\n%f",Math.pow(2, 32)-1);
	}
	
	public static void test3() throws IOException {
		SocketAddress proxyAddress = new InetSocketAddress("localhost", 1080);
		Proxy proxy = new Proxy(Proxy.Type.SOCKS, proxyAddress);
		
		Socket socket = new Socket(proxy);
		SocketAddress remote = new InetSocketAddress("www.baidu.com", 25);
		socket.connect(remote);
		
		socket.close();
	}
	
	public static void test4() {
		char[] password = System.console().readPassword();
		System.out.println("Your input: "+String.valueOf(password));
	}
	
	public static void test5() throws IOException {
		AsynchronousSocketChannel serverChannel = AsynchronousSocketChannel.open();
		serverChannel.connect(
				new InetSocketAddress("127.0.0.1", 10086), 
				ByteBuffer.allocate(100), 
				new CompletionHandler<Void,ByteBuffer>() {

			@Override
			public void completed(Void result, ByteBuffer attachment) {
				
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				
			}
			
		});
	}
	
	public static void test6() throws IOException {
		NetworkChannel channel = SocketChannel.open();
		channel.setOption(StandardSocketOptions.SO_LINGER, 240);
	}
	
	public static void test7() throws IOException {
		DatagramChannel channel = DatagramChannel.open();
		SocketAddress target = new InetSocketAddress("127.0.0.1", 1009);
		ByteBuffer buffer = ByteBuffer.allocate(1);
		for(byte i = 0; i < 5; i++) {
			buffer.put(i);
			buffer.flip();
			channel.send(buffer, target);
			buffer.clear();
		}
	}

}
