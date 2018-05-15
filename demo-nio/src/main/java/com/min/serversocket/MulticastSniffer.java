package com.min.serversocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastSniffer {

	public static void main(String[] args) throws UnknownHostException {
		InetAddress group = InetAddress.getByName(/*"239.255.255.250"*/"all-systems.mcast.net");
		int port = /*1900*/4000;
		
		MulticastSocket ms = null;
		try {
			ms = new MulticastSocket(port);
			ms.joinGroup(group);
			
			byte[] buffer = new byte[8192];
			while(true) {
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				ms.receive(dp);
				String s = new String(dp.getData(), "ISO-8859-1");
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ms != null) {
				try {
					ms.leaveGroup(group);
					ms.close();
				} catch (Exception e2) {}
			}
		}
	}

}
