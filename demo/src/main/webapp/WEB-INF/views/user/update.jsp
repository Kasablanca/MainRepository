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
<title>update</title>
<link rel="stylesheet" href="css/common.css">
</head>
<body>
<div class="container" id="photoDiv"></div>
<div class="container">
	<form action="user/updUser" method="post" enctype="multipart/form-data" id="mainForm">
		userId:<input name="userId" value="${user.userId }" ><br>
		userName:<input name="userName" value="${user.userName }" ><br>
		userNo:<input name="userNo" value="${user.userNo }" ><br>
		userTitle:<input name="userTitle" value="${user.userTitle }" ><br>
		userSex:<input name="userSex" value="${user.userSex }" ><br>
		birthday:<input name="birthday" value="${birthdayStr }" ><br>
		identityCard:<input name="identityCard" value="${user.identityCard }" ><br>
		telNo:<input name="telNo" value="${user.telNo }" ><br>
		email:<input name="email" value="${user.email }" ><br>
		tencent:<input name="tencent" value="${user.tencent }" ><br>
		wechat:<input name="wechat" value="${user.wechat }" ><br>
		address:<input name="address" value="${user.address }" ><br>
		userStatus:<input name="userStatus" value="${user.userStatus }" ><br>
		<input type="submit">
	</form>
	<input name="userPortraitFile" type="file" hidden="hidden" id="file" >
</div>
<div id="div1"></div>

<script src="js/jquery-1.11.0.min.js"></script>
<script src="js/utils.js"></script>
<script src="resources/user/update.js"></script>
<script>
var photoName = "${user.userPortrait}";
</script>
</body>
</html>