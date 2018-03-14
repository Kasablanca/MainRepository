package cn.lm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO相关工具类<br>
 * @author L.M.<br>
 *
 */
public class IoUtils {
	
	/**
	 * 保存输入流至输出流,但不关闭输入流<br>
	 * @param is 输入流<br>
	 * @param os 输出流<br>
	 * @throws IOException IO异常<br>
	 */
	public static void copyStream(InputStream is,OutputStream os) throws IOException{
		byte[] buf = new byte[4096];
		int len=0;

		while((len=is.read(buf)) != -1){
			os.write(buf, 0, len);
		}
	}
	
}
