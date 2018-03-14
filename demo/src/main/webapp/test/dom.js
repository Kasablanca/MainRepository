$(function (){
	$("#button").click(function (){
		var fd = new FormData();
		fd.append("username","MIN.LEE");
		
		var blob = new Blob(['<a id="a"><b id="b">hey!</b></a>'],{type:"text/html"});
		fd.append("qrcode",blob);
		
		/*var ab = new ArrayBuffer(1024);
		var ta = new Uint8Array(ab,0,1024);
		for(var i=0;i<128;i++){
			ta[i]=i;
		}
		var blob = new Blob(ab,{type:"application/octet-binary"});
		fd.append("qrcode",blob);*/
		
		/*ab = new ArrayBuffer(512);
		var dv = new DataView(ab,0,512);
		for(var i=0;i<64;i++){
			dv.setUint8(i,64-i);
		}
		blob = new Blob(ab,{type:"text/plain"});
		fd.append("qrcode",blob);*/
		
		ab = new ArrayBuffer(256);
		ta = new Uint32Array(ab);
		for(var i=0;i<64;i++){
			ta[i]=i*2;
		}
		blob = new Blob(ta,{type:"application/octet-binary"});
		fd.append("qrcode",blob);
		
		var xhr = new XMLHttpRequest();
		xhr.open("post","test/upload");
		xhr.onreadystatechange = function (){
			if(xhr.readyState != 4) return;
			if(xhr.status != 200){
				alert(xhr.status);
				return;
			}
			alert(xhr.responseText);
		};
		xhr.send(fd);
	});
	
	document.getElementById("left").onclick = function (){
		scrollLeft +=5;
	}
	document.getElementById("right").onclick = function (){
		scrollLeft -=5;
	}
	document.getElementById("up").onclick = function (){
		scrollTop +=5;
	}
	document.getElementById("down").onclick = function (){
		scrollLeft -=5;
	}
});