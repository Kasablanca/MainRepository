package cn.lm.service.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lm.Result;
import cn.lm.mapper.MenuMapper;
import cn.lm.model.Menu;
import cn.lm.model.MenuNode;
import cn.lm.service.MenuService;
import cn.lm.utils.ExcelUtil;

@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuMapper menuMapper;

	@Override
	public Result getMenuTree(Integer menuId) {
		List<MenuNode> childNodes = menuMapper.getMenuTree(menuId);
		MenuNode currentNode = menuMapper.getMenuNode(menuId);
		processNode(childNodes, currentNode);
		
		Result result = Result.getSuccessResult();
		result.setData(childNodes);
		return result;
	}
	
	private static void processNode(List<MenuNode> childNodes, MenuNode currentNode) {
		for(MenuNode node:childNodes) {
			node.setParent(currentNode);
			if(currentNode==null)
				node.setPath(node.getText());
			else
				node.setPath(currentNode.getText()+MenuNode.PATH_DELIMITER+node.getText());
		}
		for(MenuNode node:childNodes)
			processNode(node.getChildren(),node);
	}

	@Override
	public Result add(MenuNode menuNode) {
		Result result = Result.getErrorResult();
		
		int count = menuMapper.getCountByName(menuNode.getText(), menuNode.getParentId());
		if(count > 0) {
			result.setMessage("菜单名字重复");
			return result;
		}
		
		Menu menu = new Menu();
		menu.setId(menuNode.getId());
		menu.setName(menuNode.getText());
		menu.setParentId(menuNode.getParentId());
		menu.setStatus((byte) 0);
		menuMapper.insert(menu);
		
		result = Result.getSuccessResult();
		result.setData(menu.getId());
		return result;
	}

	@Override
	public Result edit(MenuNode menuNode) {
		Result result = Result.getErrorResult();
		
		Menu parent = menuMapper.selectByPrimaryKey(menuNode.getId());
		int count = menuMapper.getCountByName(menuNode.getText(), parent==null?null:parent.getId());
		if(count > 0) {
			result.setMessage("菜单名字重复");
			return result;
		}
		
		Menu menu = new Menu();
		menu.setId(menuNode.getId());
		menu.setName(menuNode.getText());
		menuMapper.updateByPrimaryKeySelective(menu);
		
		return Result.getSuccessResult();
	}

	@Override
	public Result delete(Integer menuId) {
		int count = menuMapper.deleteByPrimaryKey(menuId);
		Result result = Result.getSuccessResult();
		result.setMessage(String.valueOf(count));
		return result;
	}

	@Override
	public void downloadExcel(HttpServletResponse resp) {
		Workbook wb = getDownloadExcel();
		try {
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment;filename="+new String("菜单.xls".getBytes(),"ISO-8859-1"));
			wb.write(resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Workbook getDownloadExcel() {
		Workbook wb = new HSSFWorkbook();
		
		Sheet sheet = wb.createSheet("default");
		List<MenuNode> children = menuMapper.getMenuTree(null);
		processNode(children, null, wb, sheet, 0);
		wb.setSheetHidden(0, true);
		
		sheet = wb.createSheet();
		int colnum = 10;
		for(int rownum = 0; rownum < 500; rownum++) {
			ExcelUtil.addCellFormulaConstraint(sheet, rownum, colnum+0, "_");
			ExcelUtil.addCellFormulaConstraint(sheet, rownum, colnum+1, ExcelUtil.indirect("$"+CellReference.convertNumToColString(colnum+0)+"$"+(rownum+1)));
			ExcelUtil.addCellFormulaConstraint(sheet, rownum, colnum+2, ExcelUtil.concatenateAndIndirect(",\"_\",", (rownum+1), colnum+0, colnum+1));
			ExcelUtil.addCellFormulaConstraint(sheet, rownum, colnum+3, ExcelUtil.concatenateAndIndirect(",\"_\",", (rownum+1), colnum+0, colnum+1, colnum+2));
			ExcelUtil.addCellFormulaConstraint(sheet, rownum, colnum+4, ExcelUtil.concatenateAndIndirect(",\"_\",", (rownum+1), colnum+0, colnum+1, colnum+2, colnum+3));
		}
		return wb;
	}
	
	private void processNode(List<MenuNode> children, MenuNode current, Workbook wb, Sheet sheet,int rownum) {
		if(children == null || children.size() == 0)
			return;
		
		Row row = sheet.createRow(rownum);
		
		int colnum = 0;
		if(current == null)
			for(MenuNode child : children) {
				child.setPath(child.getText());
				ExcelUtil.setCellValue(row, colnum++, child.getText());
			}
		else
			for(MenuNode child : children) {
				child.setPath(current.getPath()+"_"+child.getText());
				ExcelUtil.setCellValue(row, colnum++, child.getText());
			}
		Name name = wb.createName();
		if(current == null)
			name.setNameName("_");
		else
			name.setNameName(current.getPath());
		name.setRefersToFormula("default!"+ExcelUtil.numberToString(rownum+1, colnum-1));
		
		for(MenuNode child : children)
			processNode(child.getChildren(),child,wb,sheet,++rownum);
	}

}
