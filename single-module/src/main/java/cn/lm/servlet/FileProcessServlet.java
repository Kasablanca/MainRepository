package cn.lm.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/file")
public class FileProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getContentType());
		
		InputStream is = request.getInputStream();
		FileOutputStream fos = new FileOutputStream("D:/Resource/upload.data");
		
		byte[] buf = new byte[4096];
		int len;
		while((len=is.read(buf)) != -1){
			fos.write(buf, 0, len);
		}
		fos.close();
		
		char[] cbuf = new char[4096];
		File file = new File("D:/Chrome/multi-module-root/pom.xml");
		response.setContentLength((int) file.length());
		FileReader fr = new FileReader(file);
		Writer w = response.getWriter();
		while((len=fr.read(cbuf)) != -1){
			w.write(cbuf,0,len);
		}
		fr.close();
		w.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
