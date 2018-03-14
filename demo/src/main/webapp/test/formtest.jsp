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
<title>FORM TEST</title>
</head>
<body style="width: 5000px;height: 5000px;background-image: url('timg.jpg');margin: 0 0;">
<form id="mainForm" style="background-color: #F00;display: inline-block;vertical-align: top;">
	<div><label>username:</label><input name="username" /></div>
	<div>
		<label>sex:</label>
		<input type="radio" name="sex" value="man"/>Man
		<input type="radio" name="sex" value="women"/>Women
	</div>
	<div><label>党员:</label><input name="method" type="checkbox"/></div>
	<div><input type="submit" /></div>
	<div><button type="button" id="btn">SUBMIT</button></div>
</form>
<div id="div" style="width: 400px;height: 400px;clip: rect(0px 300px 300px 0px);border: 5px solid blue;display: inline-block;">
	<div style="width: 200px;height: 200px;background-image: url('Chrysanthemum.jpg');border: 5px solid yellow;display: inline-block;"></div>
</div>
<script>
window.onload = function (){
	document.getElementById("btn").onclick = function (){
		console.log(document.forms.mainForm.elements);
	}
	
	var div = document.getElementById("div");
	console.log(div.style.width);
}
</script>
</body>
</html>