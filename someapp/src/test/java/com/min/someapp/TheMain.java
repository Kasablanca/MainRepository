package com.min.someapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TheMain {

	public static void main(String[] args) {
		Date now = new Date(1531635474000L);
		System.out.println(now);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(now));
	}

}
