package com.lee.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lee.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("fastjson")
@Api(value = "FastJson测试", tags = { "测试接口" })
public class FastJsonController {

	@GetMapping("/test/{name}")
	@ResponseBody
	@ApiOperation("获取用户信息")
	@ApiImplicitParam(name = "name", value = "用户名", dataType = "string", paramType = "query")
	public User test(@PathVariable("name")String name) {
		User user = new User();

		user.setId(1);
		user.setUsername(name);
		user.setPassword("杰克123");
		user.setBirthday(new Date());

		return user;
	}

}
