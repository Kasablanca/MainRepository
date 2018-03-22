package com.min;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestCase {

	public static void main(String[] args) {

		printContentType("C:\\Users\\Administrator\\Pictures/monkey.jpg");
		printContentType("C:\\Users\\Administrator\\Pictures/1.bmp");
		printContentType("D:\\Chrome/memory_model-1_0-pfd-spec.pdf");
		printContentType("D:\\Chrome/ppt");
		printContentType("D:\\Chrome/采购明细查询报表.xls");

	}

	private static void printContentType(String pathToFile) {

		Path path = Paths.get(pathToFile);
		String contentType = null;
		try {
			contentType = Files.probeContentType(path);
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println("File content type is : " + contentType);
	}

}
