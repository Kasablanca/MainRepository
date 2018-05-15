<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
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
	InputStream is = new FileInputStream("F:/MainRepository/.gitignore");
	byte[] b = new byte[is.available()];
	is.read(b);
	is.close();
	/* 
	out.println("<textarea style=\"width: 1000px;height:800px;\">");
	//out.println(JavaClassExecutor.execute(b));
	out.print(new String(b));
	out.println("</textarea>");
	out.println(); */
%>
<%!
	public void jspInit(){
		//System.out.println("jspInit()!");
	}
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
		<%-- <%@ include file="/test/NewFile.html" %> --%>
<jsp:element name="xmlElement">
<jsp:attribute name="xmlElementAttr">
   属性值
</jsp:attribute>
<jsp:body>
   XML 元素的主体
</jsp:body>
</jsp:element>
		<%-- <jsp:include page="test/NewFile.html" flush="true" ></jsp:include> --%>
		<!-- abc -->
	</form>
</div>
<%-- <jsp:forward page=""></jsp:forward> --%>
<%-- <jsp:include page="js/utils.js"></jsp:include> --%>
<%-- <jsp:body></jsp:body>
<c:catch var=""></c:catch>
<select name="student">
	<c:forEach items="students" var="student" varStatus="status">
		<option value="${student.id }">${student.name }--${status }</option>
	</c:forEach>
</select> --%>
<jsp:scriptlet>
	/* System.out.println("jsp:scriptlet");
	System.out.println(a); */
</jsp:scriptlet>
<jsp:declaration>
	int a = 1;
</jsp:declaration>
<jsp:include page="/test/NewFile.html"></jsp:include>
<script src="js/jquery-1.11.0.min.js"></script>
<script src="js/utils.js"></script>
<script>
window.onload = function (){
	
};
</script>
</body>
</html>