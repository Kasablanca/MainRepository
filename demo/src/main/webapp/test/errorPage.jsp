<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Show Error Page</title>

<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/jqgrid/ui.jqgrid-bootstrap.css" />
<script src="js/jquery-3.2.1.js" type="text/javascript"></script>
<script src="js/bootstrap.js" type="text/javascript"></script>

</head>
<body>
	<div class="container">
		<h1>Opps...</h1>
		<table style="width: 100%" border="1">
			<tr valign="top">
				<td width="40%"><b>Error:</b></td>
				<td>${pageContext.exception}</td>
			</tr>
			<tr valign="top">
				<td><b>URI:</b></td>
				<td>${pageContext.errorData.requestURI}</td>
			</tr>
			<tr valign="top">
				<td><b>Status code:</b></td>
				<td>${pageContext.errorData.statusCode}</td>
			</tr>
			<tr valign="top">
				<td><b>Stack trace:</b></td>
				<td><c:forEach var="trace"
						items="${pageContext.exception.stackTrace}">
						<p>${trace}</p>
					</c:forEach></td>
			</tr>
		</table>
	</div>
</body>
</html>