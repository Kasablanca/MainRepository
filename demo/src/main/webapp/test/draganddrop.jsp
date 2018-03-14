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
<style>
#droptarget {
	border: solid 2px black;
	width: 200px;
	height: 200px;
}
#droptarget.active {
	border: solid 4px red;
}
.test {
	border: solid 4px red;
}
</style>
</head>
<body>
<div id="droptarget">Drop Image Files Here</div>
</body>
<script>
window.onload = function (){
	var droptarget = document.getElementById("droptarget");
	
	droptarget.ondragenter = function (e){
		var types = e.dataTransfer.types;
		if(!types || (types.contains && types.cantains("Files"))
				|| (types.indexOf && types.indexOf("Files") != -1)){
			droptarget.classList.add("active");
			return false;
		}
	};
	
	droptarget.ondragleave = function (e){
		droptarget.classList.remove("active");
	};
	
	droptarget.ondragover = function (e){ return false; };
	
	droptarget.ondrop = function (e){
		var files = e.dataTransfer.files;
		for(var i=0; i < files.length; i++){
			var type = files[i].type;
			if(type.substring(0,6) !== "image/") continue;
			var img = document.createElement("img");
			img.src = URL.createObjectURL(files[i]);
			img.onload = function (){
				this.width=100;
				droptarget.appendChild(this);
				URL.revokeObjectURL(this.src);
			}
		}
		
		droptarget.classList.remove("active");
		return false;
	};
}
</script>
</html>