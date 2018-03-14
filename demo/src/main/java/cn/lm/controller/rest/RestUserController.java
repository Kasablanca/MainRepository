package cn.lm.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.lm.Result;
import cn.lm.model.TUser;
import cn.lm.service.UserService;

@RestController
@RequestMapping("/rest")
public class RestUserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/user")
	public Result addUser(TUser user,@RequestParam("userPortraitFile")MultipartFile file){
		try {
			return userService.addUser(user, file);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.getErrorResult();
		}
	}
	
	@DeleteMapping("/user/{userId}")
	public Result delete(@PathVariable("userId")String userId) {
		try {
			return userService.delete(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.getErrorResult();
		}
	}
	
	@PutMapping("/user")
	public Result update(TUser user,@RequestParam("userPortraitFile")MultipartFile file){
		try {
			return userService.update(user, file);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.getErrorResult();
		}
	}
	
	@GetMapping("/user/{userId}")
	public Result find(@PathVariable("userId")String userId) {
		try {
			return userService.find(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.getErrorResult();
		}
	}
	
	@GetMapping("/user")
	public Result get(){
		try {
			return userService.get();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.getErrorResult();
		}
	}
}
