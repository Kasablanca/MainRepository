<!-- <?xml version="1.0" encoding="UTF-8" ?> -->
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
<title>DOM TEST</title>
</head>
<body>
<div style="width: 5000px;height: 5000px;background-color: orange;">
<button style="position: fixed;top: 500px;left: 500px;" id="left">left</button>
<button style="position: fixed;top: 500px;left: 550px;" id="right">right</button>
<button style="position: fixed;top: 500px;left: 650px;" id="up">up</button>
<button style="position: fixed;top: 500px;left: 700px;" id="down">down</button>
</div>
</body>
<script src="js/jquery-3.2.1.js"></script>
<script src="dom.js"></script>
</html>