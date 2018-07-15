package com.min.someapp.utils;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;

public class ExcelUtil {

	public static void addConstraint(Sheet sheet,int firstRow,int lastRow,int colIndex,String[] values) {
		CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, colIndex, colIndex);
		DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(values);
		DataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
		dataValidation.setSuppressDropDownArrow(false);
		sheet.addValidationData(dataValidation);
	}
	
	/**
	 * 单元格添加下拉菜单<br>
	 * @param sheet
	 * @param rownum
	 * @param colnum
	 * @param formula 引用公式<br>
	 */
	public static void addCellFormulaConstraint(Sheet sheet,int rownum,int colnum,String formula){
		CellRangeAddressList addressList = new CellRangeAddressList(rownum, rownum, colnum, colnum);
		DVConstraint dvConstraint = DVConstraint.createFormulaListConstraint(formula);
		DataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
		dataValidation.setSuppressDropDownArrow(false);
		sheet.addValidationData(dataValidation);
	}
	
	public static String numberToString(int rownum, int colnum) {
		return "$A$"+rownum+":$"+CellReference.convertNumToColString(colnum)+"$"+rownum;
	}
	
	public static void setCellValue(Row row, int colnum, String value) {
		Cell cell = row.createCell(colnum);
		cell.setCellType(CellType.STRING);
		cell.setCellValue(value);
	}
	
	public static String indirect(String address) {
		return "INDIRECT(" + address + ")";
	}
	
	public static String concatenate(String str1, String str2, String ...strarr) {
		StringBuilder sb = new StringBuilder();
		sb.append("CONCATENATE(").append(str1).append(",").append(str2);
		for(String str : strarr)
			sb.append(",").append(str);
		return sb.append(")").toString();
	}
	
	public static String concatenateAndIndirect(String seperator,int rownum,int colnum1,int colnum2,int ...colarr) {
		StringBuilder sb = new StringBuilder();
		sb.append("CONCATENATE(")
		   .append("$").append(CellReference.convertNumToColString(colnum1)).append("$").append(rownum)
		   .append(seperator)
		   .append("$").append(CellReference.convertNumToColString(colnum2)).append("$").append(rownum);
		for(int colnum : colarr)
			sb.append(seperator).append("$").append(CellReference.convertNumToColString(colnum)).append("$").append(rownum);
		return indirect(sb.append(")").toString());
	}
}
