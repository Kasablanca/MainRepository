package com.min.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IoUtils {

	public static boolean copy(InputStream in,String filePath) {
    	try(BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath))) {
			byte[] buf = new byte[1024*10];
			int len = 0;
			while((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public static boolean copy(InputStream in,File targetFile) {
    	return copy(in,targetFile.getAbsolutePath());
    }
	
}
