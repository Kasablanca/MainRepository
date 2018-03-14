<?xml version="1.0" encoding="UTF-8" ?>
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
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/jqgrid/ui.jqgrid-bootstrap.css" />
</head>
<body>
	<div class="container" style="background-color: #ddd;">
		<form id="mainForm" class="form-horizontal" enctype="multipart/form-data" action="<%=basePath %>test/upload" method="post">
			<div class="form-group">
				<label for="username" class="col-sm-2 control-label">用户名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名"/>
				</div>
			</div>
			<div class="form-group">
				<label for="qrcode" class="col-sm-2 control-label">二维码</label>
				<div class="col-sm-10">
					<!-- <input type="file" class="form-control" id="qrcode" name="qrcode" placeholder="请选择二维码"/> -->
					<div id="qrcode" style="width: 100px;height: 100px;"></div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 col-sm-offset-5">
					<input type="submit" class="form-control btn-primary" value="提交"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 col-sm-offset-5">
					<div id="container"></div>
				</div>
			</div>
		</form>
	</div>
<script src="js/jquery-3.2.1.js"></script>
<script src="js/jquery-qrcode.js"></script>
<script src="js/bootstrap.js"></script>
<script>
$(function (){
	$("#mainForm").submit(function (e){
		e.preventDefault();
		
		new Promise(function (resolve,reject){
			var canvas = $("#qrcode canvas")[0];
			canvas.toBlob(function (blob){
				resolve(blob);
			},"image/jpg");
		}).then(function (result){
			var fd = new FormData();
			fd.append("name","MR.RIGHT");
			if(window.qrcode){
				fd.append("qrcode",result);
			}
			
			var xhr = new XMLHttpRequest();
			xhr.open("post","test/upload");
			xhr.send(fd);
		});
	});
	$("#username").change(function (e){
		$("#qrcode canvas").remove();
		if(this.value){
			$("#qrcode").qrcode({
				size: 100
			});
		}
	});
});
</script>
</body>
</html>