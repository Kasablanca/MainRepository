package cn.lm.service;

import javax.servlet.http.HttpServletResponse;

import cn.lm.Result;
import cn.lm.model.MenuNode;

public interface MenuService {

	Result getMenuTree(Integer menuId);

	Result add(MenuNode menu);

	Result edit(MenuNode menu);

	Result delete(Integer menuId);
	
	void downloadExcel(HttpServletResponse resp);
}
