package com.min.serversocket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DaytimeUdpClient {
	
	private static final int PORT = 13;
	private static final String HOST_NAME = "time.nist.gov";

	public static void main(String[] args) {
		try (DatagramSocket socket = new DatagramSocket(0)) {
			socket.setSoTimeout(10000);
			InetAddress host = InetAddress.getByName(HOST_NAME);
			DatagramPacket request = new DatagramPacket(new byte[1], 1, host, PORT);
			DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
			socket.send(request);
			socket.receive(response);
			String result = new String(response.getData(), 0, response.getLength(), "US-ASCII");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
