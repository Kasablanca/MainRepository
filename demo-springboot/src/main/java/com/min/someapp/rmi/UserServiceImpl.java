package com.min.someapp.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.min.someapp.dao.model.User;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {

	private static final long serialVersionUID = 1L;

	protected UserServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public List<User> getList() throws RemoteException {
		List<User> list = new ArrayList<>();
		User user = new User();
		user.setId(1);
		user.setAccName("test-account");
		user.setUsername("test-username");
		user.setPassword("test-password");
		list.add(user);
		
		return list;
	}
	
	public static void main(String[] args) throws RemoteException, MalformedURLException {
		UserService userService = new UserServiceImpl();
		LocateRegistry.createRegistry(5008);//定义端口号
		Naming.rebind("rmi://127.0.0.1:5008/UserService", userService);
		System.out.println("服务已启动");
	}

}
