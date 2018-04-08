package cn.lm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/jsonp")
public class JsonpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonp = request.getParameter("jsonp");
		if(jsonp == null){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
		}
		
		StringBuilder responseText = new StringBuilder();
		responseText.append(jsonp)
					.append("({")
					.append("'name': 'MR.RIGHT','age': 28")
					.append("});");
		
		response.getWriter().append(responseText.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
