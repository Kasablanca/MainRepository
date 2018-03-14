package cn.lm.ws;

import java.io.File;
import java.util.List;

import javax.jws.WebMethod;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.lm.Result;
import cn.lm.mapper.TUserMapper;
import cn.lm.model.TUser;
import cn.lm.model.vo.UserVO;

//@Component
//@WebService(serviceName="UserService")
public class UserServiceEndpoint {
	
	@Autowired
	private TUserMapper userMapper;

	@WebMethod
	public Result delete(String userId) {
		String userPhoto = userMapper.selectByPrimaryKey(userId).getPortrait();
		if(StringUtils.isNotBlank(userPhoto)){
			File userPhotoFile = new File("D:/Resource");
			if(userPhotoFile.exists()){
				userPhotoFile.delete();
			}
		}
		int count = userMapper.deleteByPrimaryKey(userId);
		if(count != 1){
			return Result.getErrorResult();
		}

		return Result.getSuccessResult();
	}
	
	@WebMethod
	public Result find(String userId) {
		TUser user = userMapper.selectByPrimaryKey(userId);

		Result result = Result.getSuccessResult();
		result.setData(user);
		return result;
	}
	
	@WebMethod
	public Result get() {
		List<UserVO> list = userMapper.getRecordList();

		Result result = Result.getSuccessResult();
		result.setData(list);
		return result;
	}
	
}
