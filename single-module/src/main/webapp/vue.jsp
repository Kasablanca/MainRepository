<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Vue.js</title>
<script src="https://cdn.bootcss.com/vue/2.4.2/vue.min.js"></script>

<style type="text/css">

</style>

</head>
<body>
<div id="example">
</div>
<script>
//定义
var MyComponent = Vue.extend({
    template: '<div>A custom component!</div>'
})

// 注册
Vue.component('my-component', MyComponent)

// 创建根实例
new Vue({
    el: '#example'
})
</script>
</body>
</html>