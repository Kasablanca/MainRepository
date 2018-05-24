package cn.lm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lm.Result;
import cn.lm.model.TUser;
import cn.lm.other.DefaultSchedulerAndExecutor;
import cn.lm.other.TaskExecutorDemo;

@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TaskExecutorDemo task;
	
	@Autowired
	private DefaultSchedulerAndExecutor task2;
	
	@ResponseBody
	@RequestMapping("/exec")
	public String exec() {
		task.printMessages();
		
		return "a中文";
	}
	
	@ResponseBody
	@RequestMapping("/exec2")
	public String exec2() {
		task2.asyncTask(2);
		System.out.println(this.getClass().getName());
		
		return "OK";
	}
	
	@ResponseBody
	@RequestMapping("/login")
	public Result login(TUser user) {
		System.out.println(user.getUserName());
		System.out.println(user.getUserName().length());
		
		return Result.getSuccessResult();
	}
	
	@ResponseBody
	@RequestMapping("user")
	public Result add(TUser user) {
		Result result = Result.getSuccessResult();
		result.setData(user);
		return result;
	}
	
}
