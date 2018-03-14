package cn.lm.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.lm.Result;
import cn.lm.mapper.TUserMapper;
import cn.lm.model.TUser;
import cn.lm.model.vo.UserVO;
import cn.lm.service.UserService;
import cn.lm.utils.IoUtils;
import cn.lm.utils.MyStringUtils;

@Service
@CacheConfig(cacheNames="user")
public class UserServiceImpl implements UserService {

	@Autowired
	private TUserMapper userMapper;

	@Override
	@CachePut()
	public Result addUser(TUser user, MultipartFile file) {
		if(file != null && file.getSize()>0){
			try {
				String extentionName = MyStringUtils.getExtentionName(file.getOriginalFilename());
				String fileName = UUID.randomUUID().toString()+extentionName;

				InputStream is = file.getInputStream();
				OutputStream os = new FileOutputStream("D:/Resource/"+fileName);

				IoUtils.copyStream(is, os);
				os.close();

				user.setPortrait(fileName);
			} catch (Exception e) {
				e.printStackTrace();
				return Result.getErrorResult();
			}
		} else {
			user.setPortrait("");
		}

		userMapper.insertSelective(user);
		return Result.getSuccessResult();
	}
	
	@Override
	@CacheEvict
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

	@Override
	@CacheEvict
	public Result update(TUser user, MultipartFile file) {
		String oldFileName = userMapper.selectByPrimaryKey(user.getId()).getPortrait();
		if(StringUtils.isNotBlank(oldFileName)){
			File photo = new File("D:/Resource/"+oldFileName);
			if(photo.exists()){
				photo.delete();
			}
			user.setPortrait("");
		}

		if(file != null && file.getSize()>0){
			try {
				String extentionName = MyStringUtils.getExtentionName(file.getOriginalFilename());
				String fileName = UUID.randomUUID().toString()+extentionName;

				InputStream is = file.getInputStream();
				OutputStream os = new FileOutputStream("D:/Resource/"+fileName);

				IoUtils.copyStream(is, os);
				os.close();

				user.setPortrait(fileName);
			} catch (Exception e) {
				e.printStackTrace();
				return Result.getErrorResult();
			}
		}

		userMapper.updateByPrimaryKeySelective(user);
		return Result.getSuccessResult();
	}

	@Override
	@Cacheable
	public Result find(String userId) {
		TUser user = userMapper.selectByPrimaryKey(userId);

		Result result = Result.getSuccessResult();
		result.setData(user);
		return result;
	}

	@Override
	@Cacheable
	public Result get() {
		List<UserVO> list = userMapper.getRecordList();

		Result result = Result.getSuccessResult();
		result.setData(list);
		return result;
	}

}
