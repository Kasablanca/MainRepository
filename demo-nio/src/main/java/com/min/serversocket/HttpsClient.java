package com.min.serversocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class HttpsClient {

	public static void main(String[] args) {
		final int port = 443;
		final String host = "www.usps.com";
		
		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket socket = null;
		try {
			socket = (SSLSocket) factory.createSocket(host, port);
			
			String[] supported = socket.getSupportedCipherSuites();
			socket.setEnabledCipherSuites(supported);
			//System.out.println(Arrays.toString(supported));
			
			Writer out = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			out.write("GET http://" + host + "/ HTTP/1.1\r\n");
			out.write("Host: " + host + "\r\n");
			out.write("\r\n");
			out.flush();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String s;
			while(!(s = in.readLine()).equals("")) {
				System.out.println(s);
			}
			System.out.println();
			
			String contentLenght = in.readLine();
			int length = Integer.MAX_VALUE;
			try {
				length = Integer.parseInt(contentLenght.trim(), 16);
			} catch (NumberFormatException e) {}
			System.out.println(contentLenght);
			
			int c;
			int i = 0;
			while((c = in.read()) != -1 && i++ < length) {
				System.out.write(c);
			}
			
			System.out.println();
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			try {
				if(socket != null) socket.close();
			} catch (IOException e2) {}
		}
	}

}
