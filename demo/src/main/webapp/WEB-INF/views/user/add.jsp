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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>add</title>
<link rel="stylesheet" href="css/common.css">
</head>
<body>
<div class="container" id="photoDiv"></div>
<div class="container">
	<form method="post" action="user/addUser" enctype="multipart/form-data">
		userName:<input name="userName" ><br>
		userNo:<input name="userNo" ><br>
		userTitle:<input name="userTitle" ><br>
		userSex:<input name="userSex" type="radio" value="0" >男
			    <input name="userSex" type="radio" value="1" >女<br>
		birthday:<input name="birthday" type="date" ><br>
		identityCard:<input name="identityCard" ><br>
		telNo:<input name="telNo" type="tel" ><br>
		email:<input name="email" type="email" ><br>
		tencent:<input name="tencent" type="number" ><br>
		wechat:<input name="wechat" ><br>
		<input name="userPortraitFile" type="file" hidden="hidden" id="file" >
		address:<input name="address" ><br>
		userStatus:<input name="userStatus" type="radio" value="0" >启用
				   <input name="userStatus" type="radio" value="1" >禁用<br>
		<input type="submit">
	</form>
</div>

<script src="js/jquery-1.11.0.min.js"></script>
<script src="js/utils.js"></script>
<script src="resources/user/add.js"></script>
</body>
</html>