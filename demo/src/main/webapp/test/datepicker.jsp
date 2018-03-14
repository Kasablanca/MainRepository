<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
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

<link rel="stylesheet" href="css/easyui/easyui.css" />
<link rel="stylesheet" href="css/easyui/color.css" />
<link rel="stylesheet" href="css/easyui/icon.css" />

</head>
<body>
	<h2>Initialize Value for DateTime</h2>
	<p>The value is initialized when DateTimeBox has been created.</p>
	<div style="margin:20px 0;"></div>
	<input class="easyui-datetimebox" value="10/11/2012 2:3:56" style="width:200px">
</body>
<script src="js/jquery-3.2.1.js"></script>
<script src="js/easyui/jquery.easyui.min.js"></script>
<script src="js/easyui/easyui-lang-zh_CN.js"></script>

<script>
const a = 0;
let b = 1;
window.onload = function (){
	consoe.log(b);
}
</script>
</html>