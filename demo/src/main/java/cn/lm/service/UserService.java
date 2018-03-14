package cn.lm.service;

import org.springframework.web.multipart.MultipartFile;

import cn.lm.Result;
import cn.lm.model.TUser;

public interface UserService {
	
	Result addUser(TUser user,MultipartFile file);
	
	Result delete(String userId);
	
	Result update(TUser user,MultipartFile file);
	
	Result find(String userId);
	
	Result get();
	
}
