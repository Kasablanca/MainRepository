package cn.lm.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/chat")
public class CometChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if("text/event-stream".equalsIgnoreCase(request.getHeader("Accept"))){
			response.setContentType("text/event-stream;charset=UTF-8");
			
			Writer writer = response.getWriter();
			writer.append("data: " + new Date().toString() + "\n");
			writer.append("\n");
			writer.flush();
		} else{
			BufferedReader reader = request.getReader();
			String message = reader.readLine();
			System.out.println(message);
			
			response.setContentType("text/plain;charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			
			System.out.println("query string:"+request.getQueryString());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
