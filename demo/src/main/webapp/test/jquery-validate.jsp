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

<style>
.error {
	color: red;
}
</style>

</head>
<body>

<div class="container" style="border-color: #ddd;">
	<form id="mainForm" method="post" action="user/register" class="form-horizontal">
		<div class="form-group">
			<label for="username" class="col-sm-2 control-label">用户名</label>
			<div class="col-sm-10">
				<input type="text" class="form-control ignore" id="username" name="username" placeholder="请输入用户名"/>
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
					<input type="radio" name="sex" value="0" required/>先生
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
				<input id="reset" type="reset" class="form-control" value="重置"/>
			</div>
			<div class="col-sm-2">
				<input type="submit" class="form-control" value="注册"/>
			</div>
		</div>
		<div class="error"></div>
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
<script src="js/jquery-validate/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery-validate/localization/messages_zh.js" type="text/javascript"></script>

<script>
$(function (){
	var validator=$("#mainForm").validate({
		debug: true,
		focusInvalid: false,
		onkeyup: false,
		submitHandler: function (form){
			alert("submit");
			//form.submit();
		},
		ignore: ".ignore",
		/*
 		errorContainer: "div.error",
		errorLabelContainer: $("#mainForm div.error"),
		wrapper: "li", */
		rules: {
			username: {
				required: true,
				rangelength: [6,16]
			},
			password: {
				required: true,
				rangelength: [6,16]
			},
			/* sex: {
				required: true
			}, */
			birthday: {
				dateISO: true
			},
			email: {
				email: true
			},
			telephone: {
				number: true
			}
		},
		messages: {
			username: {
				required: "必填",
				rangelength: $.validator.format("长度需要在{0}~{1}之间")
			},
			password: {
				required: "必填",
				rangelength: $.validator.format("长度需要在{0}~{1}之间")
			},
			sex: {
				required: "必填"
			},
			birthday: {
				dateISO: "日期格式错误"
			},
			email: {
				email: "邮箱格式错误"
			},
			telephone: {
				number: "电话号码格式错误"
			}
		}
	});
	$("#reset").click(function (){
		validator.resetForm();
	});
});
</script>
</html>