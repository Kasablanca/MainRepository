package com.syhd.ahriman.utils;

/**
 * 数组实用工具
 * @author MIN.LEE
 *
 */
public class ArrayUtils {

	public static <T> boolean contains(T[] array, T target) {
		for(T item : array) {
			if((target == null && item == null) ||target.equals(item)) {
				return true;
			}
		}
		return false;
	}
	
}
