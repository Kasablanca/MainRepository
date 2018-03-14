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
</head>
<body>
<img src="images/tyson.jpg" onclick="smear(this)"/>
</body>
<script>
function smear(img) {
	var canvas = document.createElement("canvas");
	canvas.width = img.width;
	canvas.height = img.height;
	
	var ctx = canvas.getContext("2d");
	ctx.drawImage(img,0,0);
	var pixels = ctx.getImageData(0,0,img.width,img.height);
	
	var worker = new Worker("SmearWorkder.js");
	worker.postMessage(pixels);
	
	worker.onmessage = function (e){
		var smeared_pixels = e.data;
		ctx.putImageData(smeared_pixels,0,0);
		
		img.src = canvas.toDataURL();
		worker.terminate();
		canvas.width = canvas.height = 0;
	}
}
</script>
</html>