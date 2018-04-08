window.onload = function (event){
	document.getElementById("p2").addEventListener("click",function (event){
		console.log(event);
		event.preventDefault();
	},false);
	document.getElementById("p2").addEventListener("click",function (event){
		console.log(event);
		event.stopImmediatePropagation();
		event.preventDefault();
	},false);
	document.getElementById("p2").addEventListener("click",function (event){
		console.log(event);
		event.preventDefault();
	},false);
	document.getElementById("content").addEventListener("click",function (event){
		console.log(event);
		event.preventDefault();
	},false);
	
	document.getElementById('btn').onclick = function (event){
		console.log('isFunction(RegExp):'+isFunction(RegExp));
		
		// 函数式编程
		var sum = function (x,y) {
			return x+y;
		}
		var square = function (x) {
			return x*x;
		}
		var data = [1,1,0,1,2,0,1,1,9];
		var mean = data.reduce(sum)/data.length;
		var deviations = data.map(function (x){
			return x - mean;
		});
		var stddev = Math.sqrt(deviations.map(square).reduce(sum)/(data.length-1));
		console.log('stddev='+stddev);
		
		// 高阶函数
		var even = function (n){
			return n%2 === 0;
		}
		var odd = not(even);
		console.log('odd:'+[1,3,5,7,9].every(odd));
		
		var factorial = memorize(function (n){
			return (n <= 1) ? 1 : n*factorial(n-1);
		});
		console.log('factorial(5)='+factorial(5));
		
		//不完全函数
		var f = function (x,y,z){
			return x*(y-z);
		}
		partialLeft(f,2)(3,4); // -2
		partialRight(f,2)(3,4); // 6
		partial(f,undefined,2)(3,4); // -6
		
		var not2 = partialLeft(compose,function (x){
			return !x;
		});
		var isNumber = not(isNaN);
		
		// --------------OOP----------------
		var o = new Date();
		console.log(o.__proto__ === Date.prototype);
		
		var p = {
				x: 1.0,
				y: 1.0,
				get r(){
					return Math.sqrt(this.x * this.x + this.y * this.y);
				},
				set r(newvalue){
					var oldvalue = Math.sqrt(this.x * this.x + this.y * this.y);
					var ratio = newvalue/oldvalue;
					this.x *= ratio;
					this.y *= ratio;
				},
				get theta(){
					return Math.atan2(this.x,this.y);
				}
		};
		
		console.log(Object.getOwnPropertyDescriptor({x: 1}, 'x'));
		console.log(Object.getOwnPropertyDescriptor(p, 'r'));
		
		// 扩展Object对象
		Object.defineProperty(Object.prototype,'extend',{
			writable: true,
			enumerable: false,
			configurable: true,
			value: function(o){
				var names = Object.getOwnPropertyNames(o);
				for(var i=0; i<names.length; i++){
					if(names[i] in this) continue;
					var desc = Object.getOwnPropertyDescriptor(o,names[i]);
					Object.defineProperty(this,names[i],desc);
				}
			}
		});
		
		var d = new Date();
		console.log('d:'+d);
		console.log('classof:'+classof(d));
	};
};


var whenReady = (function (){
	var funcs = [];
	var ready = false;
	
	function handler(e){
		if(ready) return;
		
		if(e.type === "readystatechange" && document.readyState !== "complete")
			return;
		
		for(var i=0;i<funcs.length;i++){
			funcs[i].call(document);
		}
		
		ready = true;
		funcs = null;
	}
	
	if(document.addEventListener){
		document.addEventListener("DOMContentLoaded",handler,false);
		document.addEventListener("readystatechange",handler,false);
		window.addEventListener("load",handler,false);
	}
	else if(document.attachEvent){
		document.attachEvent("onreadystatechange",handler);
		window.attachEvent("load",handler);
	}
	
	return function whenReady(f){
		if(ready) f.call(document);
		else funcs.push(f);
	}
}());

function classof(o){
	if(o === null) return 'Null';
	if(o === undefined) return 'Undefined';
	return Object.prototype.toString.call(o).slice(8,-1);
}

/**
 * 求最大公约数<br>
 * @param a
 * @param b
 */
function gcd(a,b){
	var t;
	if(a < b) t=b, b=a, a=t;
	while(b != 0) t=b, b=a%b, a=t;
	return a;
}

/**
 * 返回具有记忆功能的函数<br>
 * @param f 函数<br>
 * @returns {Function}
 */
function memorize(f){
	var cache = {};
	return function (){
		var key = arguments.length + Array.prototype.join(arguments,',');
		if(key in cache){
			return cache[key];
		} else{
			return cache[key] = f.apply(this,arguments);
		}
	}
}

function partialLeft(f){
	var args = arguments;
	return function (){
		var a = array(args,1);
		a = a.concat(array(arguments));
		return f.apply(this,a);
	}
}

function partialRight(f){
	var args = arguments;
	return function (){
		var a = array(arguments);
		a = a.concat(array(args,1));
		return f.apply(this,a);
	}
}

function partial(f){
	var args = arguments;
	return function(){
		var a = array(args,1);
		var i = 0, j = 0;
		for(;i<a.length;i++)
			if(a[i] === undefined)
				a[i] = arguments[j++];
		a = a.concat(array(arguments,j));
	}
}

function compose(f,g){
	return function (){
		return f.call(this,g.apply(this,arguments));
	}
}

function array(a,n){
	return Array.prototype.slice.call(a, n || 0);
}

function not(f){
	return function (){
		return !f.apply(this,arguments);
	}
}

function isFunction(f){
	return Object.prototype.toString.call(f) === '[object Function]';
}

function post(){
	var xhr = new XMLHttpRequest();
	xhr.open('post','http://localhost:8080/single-module/login');
	var content = document.getElementById('content');
	xhr.onreadystatechange = function (){
		content.innerHTML = content.innerHTML + 
		'status=' + xhr.status + 
		',statusText=' + xhr.statusText + 
		',readyState=' + xhr.readyState + 
		'<br>';
		if(xhr.readyState == 4 && xhr.status == 200){
			content.innerHTML = content.innerHTML + xhr.responseText + '<br>';
		}
	}
	xhr.send(JSON.stringify({username: 'ysh',password: '123456'}));
}