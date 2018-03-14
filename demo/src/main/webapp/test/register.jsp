<?xml version="1.0" encoding="UTF-8" ?>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/jqgrid/ui.jqgrid-bootstrap.css" />

</head>
<body>

<div class="container" style="border-color: #ddd;">
	<form method="post" action="user/register" class="form-horizontal">
		<div class="form-group">
			<label for="username" class="col-sm-2 control-label">用户名</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名"/>
			</div>
		</div>
		<div class="form-group">
			<label for="password" class="col-sm-2 control-label">密码</label>
			<div class="col-sm-10">
				<input type="password" class="form-control" id="password" name="password" placeholder="请输入密码"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">性别</label>
			<div class="col-sm-10">
				<label class="radio-inline">
					<input type="radio" name="sex" value="0"/>先生
				</label>
				<label class="radio-inline">
					<input type="radio" name="sex" value="1"/>女士
				</label>
				<label class="radio-inline">
					<input type="radio" name="sex" value="2"/>保密
				</label>				
			</div>
		</div>
		<div class="form-group">
			<label for="birthday" class="col-sm-2 control-label">出生日期</label>
			<div class="col-sm-10">
				<input type="date" class="form-control" id="birthday" name="birthday"/>
			</div>
		</div>
		<div class="form-group">
			<label for="email" class="col-sm-2 control-label">电子邮箱</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="email" name="email" placeholder="请输入电子邮箱"/>
			</div>
		</div>
		<div class="form-group">
			<label for="telephone" class="col-sm-2 control-label">电话号码</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="telephone" name="telephone" placeholder="请输入电话号码"/>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-2 col-sm-offset-4">
				<input type="reset" class="form-control" value="重置"/>
			</div>
			<div class="col-sm-2">
				<input type="submit" class="form-control" value="注册"/>
			</div>
		</div>
	</form>
	<c:if test="${errors!=null && errors.size()>0 }">
		<b>输入不合法：</b><br>
		<c:forEach var="error" items="${errors }">
			<div class="alert alert-danger">${error.defaultMessage }</div>
		</c:forEach>
	</c:if>
	<c:if test="${exception!=null }">
	<div class="alert alert-danger">服务器出现错误：${exception }</div>
	</c:if>
</div>
</body>
<script src="js/jquery-3.2.1.js" type="text/javascript"></script>
<script src="js/bootstrap.js" type="text/javascript"></script>
</html>