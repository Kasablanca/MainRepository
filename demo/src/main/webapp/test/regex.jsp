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
<title>Regular Expression</title>
<link rel="stylesheet" type="text/css"  href="bootstrap-3.3.7-dist/css/bootstrap.css"/>
<script src="js/jquery-3.2.1.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.js"></script>
</head>
<body>
	<h2>Hello World!</h2>
	<iframe id="iframe1" name="iframe">
		<iframe id="iframe2" name="iframe"></iframe>
	</iframe>
	<iframe id="iframe3" name="iframe"></iframe>
</body>
<script>
window.onerror = function (event){
	console.log(arguments);
}
window.onload = function (){
	console.log(iframe);
}
</script>
</html>