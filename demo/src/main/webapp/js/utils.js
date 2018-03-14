/*jshint esversion: 6 */

var whenReady = (function () {
	var funcs = [];
	var ready = false;

	function handler(e) {
		if (ready) return;

		if (e.type === "readystatechange" && document.readyState !== "complete")
			return;

		for (var i = 0; i < funcs.length; i++) {
			funcs[i].call(document);
		}

		ready = true;
		funcs = null;
	}

	if (document.addEventListener) {
		document.addEventListener("DOMContentLoaded", handler, false);
		document.addEventListener("readystatechange", handler, false);
		window.addEventListener("load", handler, false);
	} else if (document.attachEvent) {
		document.attachEvent("onreadystatechange", handler);
		window.attachEvent("load", handler);
	}

	return function whenReady(f) {
		if (ready) f.call(document);
		else funcs.push(f);
	}
}());

var classof = function (o) {
	if (o === null) return 'Null';
	if (o === undefined) return 'Undefined';
	return Object.prototype.toString.call(o).slice(8, -1);
};

var gcd = function (a, b) {
	var t;
	if (a < b) t = b, b = a, a = t;
	while (b != 0) t = b, b = a % b, a = t;
	return a;
};

/**
 * 返回具有记忆功能的函数<br>
 * @param f 函数<br>
 * @returns {Function}
 */
var memorize = function (f) {
	var cache = {};
	return function () {
		var key = arguments.length + Array.prototype.join(arguments, ',');
		if (key in cache) {
			return cache[key];
		} else {
			return cache[key] = f.apply(this, arguments);
		}
	};
};

var partialLeft = function (f) {
	var args = arguments;
	return function () {
		var a = array(args, 1);
		a = a.concat(array(arguments));
		return f.apply(this, a);
	};
};

var partialRight = function (f) {
	var args = arguments;
	return function () {
		var a = array(arguments);
		a = a.concat(array(args, 1));
		return f.apply(this, a);
	};
};

var partial = function (f) {
	var args = arguments;
	return function () {
		var a = array(args, 1);
		var i = 0,
			j = 0;
		for (; i < a.length; i++)
			if (a[i] === undefined)
				a[i] = arguments[j++];
		a = a.concat(array(arguments, j));
	};
};

var getJSONP = function (url, callback) {
	var cbnum = "cb" + getJSONP.counter++;
	var cbname = "getJSONP." + cbnum;

	if (url.indexOf("?") === -1)
		url += "?jsonp=" + cbname;
	else
		url += "&jsonp=" + cbname;

	var script = document.createElement("script");

	getJSONP[cbnum] = function (response) {
		try {
			callback(response);
		} finally {
			delete getJSONP[cbnum];
			script.parentNode.removeChild(script);
		}
	};

	script.src = url;
	document.body.appendChild(script);
};
getJSONP.counter = 0;

/**
 * 获取扩展名，不包括点号<br>
 * @param filename 文件名<br>
 * @returns 扩展名<br>
 */
function getExtentionName(filename) {
	var index = filename?filename.lastIndexOf(','):-1;
	if(index != -1){
		return filename.sub(index+1);
	} else {
		return null;
	}
}

function getBasePath(){
	var host = window.location.host;
	var protocol = window.location.protocol;
	var context = window.location.pathname.split("/")[1];
	
	return protocol+host+"/"+context;
}

function convertImgToDataURLviaCanvas(url, callback, outputFormat){
	var img = new Image();
	img.crossOrigin = 'Anonymous';
	img.onload = function(){
		var canvas = document.createElement('CANVAS');
		var ctx = canvas.getContext('2d');
		var dataURL;
		canvas.height = this.height;
		canvas.width = this.width;
		ctx.drawImage(this, 0, 0);
		dataURL = canvas.toDataURL(outputFormat);
		callback(dataURL);
		canvas = null; 
	};
	img.src = url;
}

function convertFileToDataURLviaFileReader(url, callback){
	var xhr = new XMLHttpRequest();
	xhr.responseType = 'blob';
	xhr.onload = function() {
		var reader  = new FileReader();
		reader.onloadend = function () {
			callback(reader.result);
		}
		reader.readAsDataURL(xhr.response);
	};
	xhr.open('GET', url);
	xhr.send();
}

function dataURLtoBlob(dataurl) {
	var arr = dataurl.split(','), 
		mime = arr[0].match(/:(.*?);/)[1],
		bstr = atob(arr[1]), 
		n = bstr.length, 
		u8arr = new Uint8Array(n);
	while(n--){
		u8arr[n] = bstr.charCodeAt(n);
	}
	return new Blob([u8arr], {type:mime});
} 