<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ page import="java.lang.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.min.jvm.classloader.*" %>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>demonstration</title>
<style type="text/css">
td {
	width: 130px;
	height: 20px;
}
</style>
</head>
<body>
<%
	InputStream is = new FileInputStream("F:/TestClass.java");
	byte[] b = new byte[is.available()];
	is.read(b);
	is.close();
	
	out.println("<textarea style=\"width: 1000px;height:800px;\">");
	out.println(JavaClassExecutor.execute(b));
	out.println("</textarea>");
%>
<div id="div" style="height: 250px;overflow: auto;width: 600px;">
	<form action="test/login" method="POST" enctype="multipart/form-data" id="mainForm" >
		userId:<input id="userId" name="userId" type="text" ><br>
		userName:<input name="userName" type="text" maxlength="10" ><br>
		birthday:<input name="birthday" type="date" ><br>
		identityCard:<input name="identityCard" type="text" ><br>
		userStatus:<input name="userStatus" type="text" ><br>
		excel:<input name="userPortraitFile" type="file" ><br>
		<input type="submit" >
	</form>
</div>
<script src="js/jquery-1.11.0.min.js"></script>
<script src="js/utils.js"></script>
<script>
window.onload = function (){
	
};
</script>
</body>
</html>