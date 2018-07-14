package com.min.someapp.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.min.someapp.dao.model.User;

public interface UserService extends Remote {
	
	List<User> getList() throws RemoteException;

}
