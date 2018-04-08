<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>please login</title>
</head>
<body>
<div style="background-color: #ccc;width: 50%;margin: auto 25%;padding: auto 0;">
	<form action="login" method="post">
		<div>username:<input name="username" value="${user.username }"></div>
		<div>age:<input name="age" value="${user.age }"></div>
		<div><input type="submit"></div>
	</form>
	<div>${result.resultMsg }</div>
</div>
</body>
</html>