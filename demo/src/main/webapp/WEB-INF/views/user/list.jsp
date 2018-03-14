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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>userlist</title>
</head>
<body>
<table>
	<tr><td>username</td><td>update</td><td>delete</td></tr>
	<c:forEach items="${list }" var="user">
		<tr>
			<td>${user.userName }</td>
			<td><a target="_self" href="user/updPage/${user.userId }">update</a></td>
			<td><a target="_self" href="user/delete/${user.userId }">delete</a></td>
		</tr>
	</c:forEach>
</table>

<script src="js/jquery-1.11.0.min.js"></script>
<script src="js/utils.js"></script>
</body>
</html>