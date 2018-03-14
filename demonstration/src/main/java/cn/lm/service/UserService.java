package cn.lm.service;

import javax.jws.WebService;

import cn.lm.Result;

@WebService(serviceName="userService")
public interface UserService {
	Result delete(String userId);
	Result find(String userId);
	Result get();
}
