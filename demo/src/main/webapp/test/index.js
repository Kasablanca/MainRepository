window.addEventListener('load',function (event){
	document.getElementById('iframe1').addEventListener('click',function (event){
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
		
		var o = {
				a: 1,
				b: 2
		};
		console.log(JSON.stringify(o));
	},true);
	
	
},true);

function range(from,to){
	var r = Object.create(range.methods);
	r.from = from;
	r.to = to;
	
	return r;
}

range.methods = {
	includes: function (x){
		return this.from <= x && x <= this.to;
	},
	foreach: function (f){
		for(var x = Math.ceil(this.from); x <= this.to; x++) f(x);
	},
	toString: function (){
		return '(' + this.from + '...' + this.to + ')';
	}
};

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
	//xhr.setRequestHeader('Origin','http://localhost:8079');
	//xhr.setRequestHeader('Access-Control-Request-Method','POST');
	xhr.send(JSON.stringify({username: 'ysh',password: '123456'}));
}