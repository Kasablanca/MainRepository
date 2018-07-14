package com.min.someapp.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import com.min.someapp.dao.model.User;

public class TheMain {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		UserService userService = (UserService) Naming.lookup("rmi://127.0.0.1:5008/UserService");
		List<User> list = userService.getList();
		System.out.println(list);
	}

}
