package com.syhd.ahriman.utils;

/**
 * 数组实用工具
 * @author MIN.LEE
 *
 */
public class ArrayUtils {

	/**
	 * 判断数组是否包含指定元素
	 * @param array 数组
	 * @param target 目标元素
	 * @return true包含，false不包含
	 */
	public static <T> boolean contains(T[] array, T target) {
		for(T item : array) {
			if((target == null && item == null) ||target.equals(item)) {
				return true;
			}
		}
		return false;
	}
	
}
