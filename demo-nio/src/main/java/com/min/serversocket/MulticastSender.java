package com.min.serversocket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastSender {

	public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
		InetAddress ia = InetAddress.getByName("all-systems.mcast.net");
		int port = 4000;
		byte ttl = 1;
		
		byte[] data = "Here's some multicast data\r\n".getBytes("ISO-8859-1");
		DatagramPacket dp = new DatagramPacket(data, data.length, ia, port);
		
		try (MulticastSocket ms = new MulticastSocket()) {
			ms.setTimeToLive(ttl);
			ms.joinGroup(ia);
			for(int i = 1; i < 10; i++) {
				ms.send(dp);
			}
			ms.leaveGroup(ia);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
