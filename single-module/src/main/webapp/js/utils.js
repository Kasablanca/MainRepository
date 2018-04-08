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