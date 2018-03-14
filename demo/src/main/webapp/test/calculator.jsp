<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
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

<link rel="stylesheet" href="css/easyui/easyui.css" />
<link rel="stylesheet" href="css/easyui/color.css" />
<link rel="stylesheet" href="css/easyui/icon.css" />

<style>
.output {
	font-weight: bold;
}
#payment {
	text-decoration: underline;
}
#graph {
	border: solid black 1px;
}
th,td {
	vertical-align: top;
}
</style>

</head>
<body>
<table>
	<tr><th>Enter Loan Data:</th>
		<td></td>
		<th>Loan Balance,Cumulative Equity,and Interest Payments</th></tr>
	<tr><td>Amount of the loan ($):</td>
		<td><input id="amount" onchange="calculate();"></td>
		<td rowspan=8>
			<canvas id="graph" width="400" height="250"></canvas></td></tr>
	<tr><td>Annual interest (%)</td>
		<td><input id="apr" onchange="calculate();"></td></tr>
	<tr><td>Repayment period (years):</td>
		<td><input id="years" onchange="calculate();"></td></tr>
	<tr><td>Zipcode (to find lenders):</td>
		<td><input id="zipcode" onchange="calculate();"></td></tr>
	<tr><td>Approximate Payments:</td>
		<td><button onclick="calculate();">Calculate</button></td></tr>
	<tr><td>Monthly payment:</td>
		<td>$<span class="output" id="payment"></span></td></tr>
	<tr><td>Total payment:</td>
		<td>$<span class="output" id="total"></span></td></tr>
	<tr><td>Total interest:</td>
		<td>$<span class="output" id="totalinterest"></span></td></tr>
	<tr><th>Sponsors:</th><td colspan=2>
		Apply for your loan with one of these fine lenders:
		<div id="lenders"></div></td></tr>
</table>
</body>
<script src="js/jquery-3.2.1.js"></script>
<script src="js/easyui/jquery.easyui.min.js"></script>
<script src="js/easyui/easyui-lang-zh_CN.js"></script>

<script>
"use strict";
function calculate() {
	var amount = document.getElementById("amount");
	var apr = document.getElementById("apr");
	var years = document.getElementById("years");
	var zipcode = document.getElementById("zipcode");
	var payment = document.getElementById("payment");
	var total = document.getElementById("total");
	var totalinterest = document.getElementById("totalinterest");
	
	var principal = parseFloat(amount.value);
	var interest = parseFloat(apr.value) /100 /12;
	var payments = parseFloat(years.value) * 12;
	
	var x = Math.pow(1+interest,payments);
	var monthly = (principal * x * interest) / (x-1);
	
	if(isFinite(monthly)) {
		payment.innerHTML = monthly.toFixed(2);
		total.innerHTML = (monthly*payments).toFixed(2);
		totalinterest.innerHTML = ((monthly*payments) - principal).toFixed(2);
		
		save(amount.value,apr.value,years.value,zipcode.value);
		
		try{
			getLenders(amount.value,apr.value,years.value,zipcode.value);
		} catch (e){}
		
		chart(principal,interest,monthly,payments);
	} else {
		payment.innerHTML = "";
		total.innerHTML = "";
		totalinterest.innerHTML = "";
		chart();
	}
}

function save(amount,apr,years,zipcode) {
	if(window.localStorage){
		localStorage.loan_amount = amount;
		localStorage.loan_apr = apr;
		localStorage.loan_years = years;
		localStorage.loan_zipcode = zipcode;
	}
}

window.onload = function (){
	if(window.localStorage && localStorage.loan_amount) {
		document.getElementById("amount").value = localStorage.loan_amount;
		document.getElementById("apr").value = localStorage.loan_apr;
		document.getElementById("years").value = localStorage.loan_years;
		document.getElementById("zipcode").value = localStorage.loan_zipcode;
	}
}

function getLenders(amount,apr,years,zipcode){
	if(!window.XMLHttpRequest) return;
	
	var ad = document.getElementById("lenders");
	if(!ad) return;
	
	var url = "<%=basePath%>test/getLenders" +
			  "?amt="+encodeURIComponent(amount) +
			  "&apr="+encodeURIComponent(apr) +
			  "&yrs="+encodeURIComponent(years) +
			  "&zip="+encodeURIComponent(zipcode);
	var req = new XMLHttpRequest();
	req.open("GET",url);
	req.send(null);
	
	req.onreadystatechange = function (){
		if(req.readyState == 4 && req.status == 200) {
			var response = req.responseText;
			var lenders = JSON.parse(response);
			
			var list="";
			for(var i=0;i<lenders.length;i++){
				list += "<li><a href='" + lenders[i].url + "'>" +
						lenders[i].name + "</a></li>";
			}
			ad.innerHTML = "<ul>" + list + "</ul>";
		}
	}
}

function chart(principal, interest, monthly, payments){
	var graph = document.getElementById("graph");
	graph.width = graph.width;
	
	if(arguments.length == 0 || !graph.getContext) return;
	
	var g = graph.getContext("2d");
	
	var width = graph.width,
		height = graph.height;
	
	function paymentToX(n) {
		return n * width / payments;
	}
	function amountToY(a) {
		return height - (a * height / (monthly * payments * 1.05));
	}
	g.moveTo(paymentToX(0),amountToY(0));
	g.lineTo(paymentToX(payments),amountToY(payments));
	amountToY(monthly * payments);
	g.lineTo(paymentToX(payments),amountToY(0));
	g.closePath();
	g.fillStyle = "#f88";
	g.fill();
	g.font = "bold 12px sans-serif";
	g.fillText("Total Interest Payments",20,20);
	
	var equity = 0;
	g.beginPath();
	g.moveTo(paymentToX(0),amountToY(0));
	for(var p=1;p<=payments;p++){
		var thisMonthsInterest = (principal - equity) * interest;
		equity +=(monthly - thisMonthsInterest);
		g.lineTo(paymentToX(p),amountToY(equity));
	}
	g.lineTo(paymentToX(payments),amountToY(0));
	g.closePath();
	g.fillStyle = "green";
	g.fill();
	g.fillText("Total Equity",20,35);
	
	var bal = principal;
	g.beginPath();
	g.moveTo(paymentToX(0),amountToY(bal));
	for(var p=1;p<=payments;p++){
		var thisMonthsInterest = bal * interest;
		bal -= (monthly - thisMonthsInterest);
		g.lineTo(paymentToX(p),amountToY(bal));
	}
	g.lineWidth = 3;
	g.stroke();
	g.fillStyle = "black";
	g.fillText("Loan Balance",20,50);
	
	g.textAlign = "center";
	var y = amountToY(0);
	for(var year=1;year * 12 <= payments;year++){
		var x = paymentToX(year * 12);
		g.fillRect(x-0.5, y-3, 1, 3);
		if(year == 1) g.fillText("Year", x, y-5);
		if(year % 5 ==0 && year * 12 !== payments)
			g.fillText(String(year), x, y-5);
	}
	
	g.textAlign = "right";
	g.textBaseline = "middle";
	var ticks = [monthly * payments, principal];
	var rightEdge = paymentToX(payments);
	for(var i=0;i<ticks.length;i++){
		var y = amountToY(ticks[i]);
		g.fillRect(rightEdge-3, y-0.5, 3, 1);
		g.fillText(String(ticks[i].toFixed(0)), rightEdge-5, y);
	}
}
</script>
</html>