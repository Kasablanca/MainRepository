<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Comet test case</title>
</head>
<body>
<input id="input" style="width: 100%">
<button id="start">start</button>
<button id="stop">stop</button>
<div>
<form action="chat" enctype="multipart/form-data" id="maiForm" method="post">
<input name="username">
<input type="file" name="file">
<input type="submit" value="submit">
</form>
</div>
<script>
window.onload = function (){
	var chat = null;
	
	document.getElementById("start").onclick = function (){
		var nick = prompt("Enter your nickname");
		var input = document.getElementById("input");
		input.focus();
		
		chat = new EventSource("chat");
		chat.onmessage = function (event){
			var msg = event.data;
			var node = document.createTextNode(msg);
			var div = document.createElement("div");
			div.appendChild(node);
			document.body.insertBefore(div,input);
			input.scrollIntoView();
		}
		
		input.onchange = function (){
			var msg = nick + ": " + input.value;
			var xhr = new XMLHttpRequest();
			xhr.open("POST","chat");
			xhr.setRequestHeader("Content-Type","text/plain;charset=UTF-8");
			xhr.send(msg);
			input.value = "";
		}
	}
	
	document.getElementById("stop").onclick = function (){
		if(chat){
			chat.close();
		}
	}
}
</script>
</body>
</html>