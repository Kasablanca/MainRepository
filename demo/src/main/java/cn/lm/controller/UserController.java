package cn.lm.controller;

import java.text.SimpleDateFormat;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.lm.Result;
import cn.lm.model.TUser;
import cn.lm.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private static SimpleDateFormat defaultDateFormt = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping("/list")
	public ModelAndView list(){
		ModelAndView mav = new ModelAndView("user/list");
		mav.addObject("list", userService.get().getData());
		
		return mav;
	}
	
	@RequestMapping("/addPage")
	public String addUserPage(){
		return "user/add";
	}
	
	@ResponseBody
	@RequestMapping("/addUser")
	public Result addUser(@Valid TUser user,BindingResult valid, @RequestParam("userPortraitFile")MultipartFile file){
		if(valid.hasErrors()){
			Result result = Result.getErrorResult();
			result.setData(valid.getAllErrors());
			return result;
		}
		try {
			return userService.addUser(user, file);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.getErrorResult();
		}
	}
	
	@RequestMapping("/updPage/{userId}")
	public ModelAndView updUserPage(@PathVariable("userId")String userId){
		ModelAndView mav = new ModelAndView("user/update");
		
		TUser user = (TUser) userService.find(userId).getData();
		mav.addObject("user", user);
		mav.addObject("birthdayStr", defaultDateFormt.format(user.getBirthday()));
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("/updUser")
	public Result updUser(@Valid TUser user,BindingResult valid, @RequestParam("userPortraitFile")MultipartFile file){
		if(valid.hasErrors()){
			Result result = Result.getErrorResult();
			result.setData(valid.getAllErrors());
			return result;
		}
		try {
			return userService.update(user, file);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.getErrorResult();
		}
	}
	
	@ResponseBody
	@RequestMapping("/delete/{userId}")
	public Result deleteUser(@PathVariable("userId")String userId) {
		return userService.delete(userId);
		//return "redirect:user/list";
	}
	
}
