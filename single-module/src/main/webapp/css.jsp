<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CSS</title>
<link rel="shortcut icon" href="favicon.ico" />
</head>
<body style="margin: 0 0;">
<div style="height: 500px;width: 600px;background-color: #ddd;">
	<button>CLICK</button>
</div>
<script>
var globalValue = {
	name: "MR.RIGHT",
	age: 18
};
window.onload = function (){
	var div = null;
	document.getElementsByTagName("button")[0].onclick = function (e){
		console.log(e.target);
	};
}
</script>
</body>
</html>