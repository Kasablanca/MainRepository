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
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>menutree</title>
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.5.4.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.5.4.1/themes/icon.css">
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.5.4.1/demo/demo.css">
</head>
<body>
	<div style="margin:20px 0;"></div>
	<div class="easyui-panel" style="padding:5px">
		<ul id="tt" class="easyui-tree"></ul>
	</div>
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="append()" data-options="iconCls:'icon-add'">Append</div>
		<div onclick="removeit()" data-options="iconCls:'icon-remove'">Remove</div>
		<div class="menu-sep"></div>
		<div onclick="expand()">Expand</div>
		<div onclick="collapse()">Collapse</div>
	</div>

<script src="js/jquery-1.11.0.min.js"></script>
<script src="js/jquery-easyui-1.5.4.1/jquery.easyui.min.js"></script>
<script src="resources/menu/tree.js"></script>
</body>
</html>