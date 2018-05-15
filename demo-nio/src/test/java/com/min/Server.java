package com.min;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(10086,0, InetAddress.getByName("192.168.2.4"));
		Socket socket;
		while((socket = server.accept()) != null) {
			new Thread(new Handler(socket)).start();
		}
		server.close();
	}
	
	static class Handler implements Runnable {
		
		Socket socket;
		
		Handler(Socket socket){
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				InputStream in = socket.getInputStream();
				byte[] buf = new byte[in.available()];
				in.read(buf);
				socket.getOutputStream().write(buf[0]*2);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException ignored) {}
			}
		}
		
	}
	
	public static void test1() throws IOException {
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		//ServerSocket ss = serverChannel.socket();
		//ss.bind(new InetSocketAddress(10010));
		serverChannel.bind(new InetSocketAddress(10010));
		serverChannel.configureBlocking(false);
		SocketChannel clientChannel = serverChannel.accept();
		clientChannel.configureBlocking(false);
		Selector selector = Selector.open();
		SelectionKey key = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		byte[] rotation = new byte[95*2];
		for(byte i = ' '; i < '~'; i++) {
			rotation[i - ' '] = i;
			rotation[i + 95 - ' '] = i;
		}
/*		
		ByteBuffer buffer = ByteBuffer.allocate(74);
		buffer.put(rotation, 0, 72);
		buffer.put((byte)'\r');
		buffer.put((byte)'\n');
		buffer.flip();
		key.attach(buffer);*/
		
		while(true) {
			selector.select();
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> it = readyKeys.iterator();
			while(it.hasNext()) {
				SelectionKey selectionKey = it.next();
				it.remove();
				
				try {
					if(selectionKey.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel connection = server.accept();
						connection.configureBlocking(false);
						connection.register(selector, SelectionKey.OP_WRITE);
						// TODO
					} else if(selectionKey.isWritable()) {
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						if(!buffer.hasRemaining()) {
							buffer.rewind();
							int first = buffer.get();
							buffer.rewind();
							int position = first - ' ' + 1;
							buffer.put(rotation, position, 72);
							buffer.put((byte)'\r');
							buffer.put((byte)'\n');
							buffer.flip();
						}
						client.write(buffer);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}

}
