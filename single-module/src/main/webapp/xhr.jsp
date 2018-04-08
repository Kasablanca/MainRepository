<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>XMLHttpRequest test case</title>
</head>
<body>
	<form id="mainForm">
		<input type="file" name="file" id="file">
	</form>
	<br>
	<button id="ajax">AJAX</button>
	<div style="width:600px;height:400px;border: 5px purple solid;overflow: scroll;" id="message"></div>
	<span>上传进度：</span>
	<div id="uploadprogress"></div>
	<span>下载进度：</span>
	<div id="progress"></div>
	<span>下载内容：</span>
	<div id="result" style="width:600px;height:400px;border: 5px purple solid;overflow: scroll;"></div>
	<button id="jsonp">JSONP</button>
	<div style="width:600px;height:400px;border: 5px purple solid;overflow: scroll;" id="jsonpmessage"></div>
<script>
window.onload = function (){
	document.getElementById('ajax').onclick = function (){
		var message = document.getElementById('message');
		var progress = document.getElementById('progress');
		var uploadprogress = document.getElementById('uploadprogress');
		var result = document.getElementById('result');
		var data = new FormData();
		data.append('file',document.getElementById("file").files[0]);
		console.log(document.getElementById("file").files);
		var xhr = new XMLHttpRequest();
		
		xhr.onloadstart = function (event){
			console.log(event);
			message.innerHTML = message.innerHTML + 'onloadstart<br>';
		}
		
		xhr.onprogress = function (event){
			progress.innerHTML = 'total: ' + event.total + ', lengthComputable: ' 
				+ event.lengthComputable + ', loaded: ' + event.loaded;
		}
		
		xhr.onload = function (event){
			console.log(event);
			message.innerHTML = message.innerHTML + 'onload<br>';
			if(xhr.status === 200){
				result.innerHTML = xhr.responseText;
			}
		}
		
		xhr.ontimeout = function (event){
			console.log(event);
			message.innerHTML = message.innerHTML + 'ontimeout<br>';
		}
		
		xhr.onabort = function (event){
			console.log(event);
			message.innerHTML = message.innerHTML + 'onabort<br>';
		}
		
		xhr.onerror = function (event){
			console.log(event);
			message.innerHTML = message.innerHTML + 'onerror<br>';
		}
		
		xhr.onloadend = function (event){
			console.log(event);
			message.innerHTML = message.innerHTML + 'onloadend<br>';
		}
		
		xhr.upload.onprogress = function (event){
			uploadprogress.innerHTML = 'total: ' + event.total + ', lengthComputable: ' 
				+ event.lengthComputable + ', loaded: ' + event.loaded;
		}
		
		xhr.upload.onload = function (event){
			console.log(event);
			message.innerHTML = message.innerHTML + 'upload.onload<br>';
		}
		
		xhr.open('POST','file');
		xhr.send(data);
	}
	
	document.getElementById("jsonp").onclick = function (event){
		var message = document.getElementById("jsonpmessage");
		
		getJSONP("jsonp",function (response){
			console.log(response);
			var text = "";
			
			for(var name in response){
				text += "," + name + ":" + response[name];
			}
			
			message.innerHTML += text.substr(1) + "<br>";
		});
	}
}

function getJSONP(url,callback){
	var cbnum = "cb" + getJSONP.counter++;
	var cbname = "getJSONP." + cbnum;
	
	if(url.indexOf("?") === -1)
		url += "?jsonp=" + cbname;
	else
		url += "&jsonp=" + cbname;
	
	var script = document.createElement("script");
	
	getJSONP[cbnum] = function (response){
		try{
			callback(response);
		}
		finally {
			delete getJSONP[cbnum];
			script.parentNode.removeChild(script);
		}
	}
	
	script.src = url;
	document.body.appendChild(script);
}
getJSONP.counter = 0;
</script>
</body>
</html>