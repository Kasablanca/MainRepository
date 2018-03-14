<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>    
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>
<input id="input" placeholder="Enter"><button id="btn">Save</button>
</body>
<script>
window.onload = function (){
	var input = document.getElementById("input");
	var btn = document.getElementById("btn");
	
	btn.onclick = function (){
		history.pushState({value: input.value},null,"history-state.jsp?value="+input.value);
	}
}
window.onpopstate = function (e){
	document.getElementById("input").value = e.state.value;
}
</script>
</html>