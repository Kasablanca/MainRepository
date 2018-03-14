package cn.lm.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.lm.Result;
import cn.lm.model.MenuNode;
import cn.lm.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("/index")
	ModelAndView index() {
		return new ModelAndView("menu/tree");
	}
	
	@RequestMapping("/menutree")
	public Result getMenuTree(Integer menuId) {
		try {
			return menuService.getMenuTree(menuId);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.getErrorResult();
		}
	}
	
	@RequestMapping("/add")
	public Result add(MenuNode menu) {
		try {
			return menuService.add(menu);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.getErrorResult();
		}
	}
	
	@RequestMapping("/edit")
	public Result edit(MenuNode menu) {
		try {
			return menuService.edit(menu);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.getErrorResult();
		}
	}
	
	@RequestMapping("/delete")
	public Result delete(Integer menuId) {
		try {
			return menuService.delete(menuId);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.getErrorResult();
		}
	}
	
	@RequestMapping("/downloadExcel")
	public void downloadExcel(HttpServletResponse resp) {
		menuService.downloadExcel(resp);
	}
}
