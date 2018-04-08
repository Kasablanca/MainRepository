<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link type="text/css" rel="stylesheet" href="index.css" title="outcss">

<style title="inlinecss">
.item { width: 300px; }
</style>

<script src="index.js" title="index.js"></script>

</head>
<body>
	<h2>Hello World!</h2>
	<button id="btn">Click</button>
	<button onclick="post();">点击</button>
	<form action="login" id="form1" name="mainForm">
		<div style="width:600px;height:600px;border: 5px red solid;" id="content">
			<button id="p2">P2</button>
			<span>中国人</span>
		</div>
		<input id="username1" onkeyup="username2.value=this.value"><br>
		<input id="username2">
	</form>

</body>
</html>