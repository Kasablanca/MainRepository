package cn.lm.utils;

public class MyStringUtils {
	
	/**
	 * 获取文件扩展名<br>
	 * @param fileName 原文件名<br>
	 * @return 后缀名或空字符串<br>
	 */
	public static String getExtentionName(String fileName){
		String extName = "";
		if(org.apache.commons.lang3.StringUtils.isNotBlank(fileName)){
			int index = fileName.lastIndexOf(".");
			if(index != -1){
				extName = fileName.substring(index);
			}
		} 
		return extName;
	}
	
}
