<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>demonstration</title>
<style type="text/css">
td {
	width: 130px;
	height: 20px;
}
</style>
</head>
<body>
<div>
	<table border="1" style="background-color: #aaa;">
		<caption>caption caption caption</caption>
		<thead>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
		</thead>
	</table>
</div>
<div id="div" style="height: 250px;overflow: auto;width: 600px;">
	<table border="1" style="background-color: #aaa;">
		<tbody>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
		</tbody>
		<tfoot>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
		</tfoot>
	</table>
</div>
<div>
	<table border="1" style="background-color: #aaa;">
		<tfoot>
			<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
		</tfoot>
	</table>
</div>
<button id="test">测试</button>
<script src="utils.js"></script>
<script src="jquery-1.11.0.min.js"></script>
<script src="index.js"></script>
<script>
window.onload = function (){
	
};
</script>
</body>
</html>